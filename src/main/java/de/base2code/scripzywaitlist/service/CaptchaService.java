package de.base2code.scripzywaitlist.service;

import de.base2code.scripzywaitlist.common.exception.InvalidCaptchaException;
import de.base2code.scripzywaitlist.config.CaptchaSettings;
import de.base2code.scripzywaitlist.response.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class CaptchaService {

    public final CaptchaSettings captchaSettings;
    private final WebClient.Builder webclientBuilder;

    public RecaptchaResponse verify(String tokenResponse) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("secret", captchaSettings.getSecret());
        formData.add("response", tokenResponse);

        return webclientBuilder.build().post()
                .uri(captchaSettings.getUrl())
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(RecaptchaResponse.class)
                .doOnSuccess(recaptchaResponse -> {
                    log.info("response verify captcha: {}", recaptchaResponse.toString());

                    if (!recaptchaResponse.isSuccess()){
                        throw new InvalidCaptchaException("reCaptcha v3 was not successfully validated");
                    }

                    if(recaptchaResponse.isSuccess() &&
                            recaptchaResponse.getScore() < captchaSettings.getThreshold()){
                        throw new InvalidCaptchaException("Low score for reCaptcha v3");
                    }
                })
                .doOnError(e -> {
                    log.error("error verify captcha : {}", e.getMessage());
                    throw new InvalidCaptchaException(e.getMessage());
                }).block();
    }
}
