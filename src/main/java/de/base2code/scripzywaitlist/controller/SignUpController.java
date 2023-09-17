package de.base2code.scripzywaitlist.controller;

import de.base2code.scripzywaitlist.common.exception.InvalidCaptchaException;
import de.base2code.scripzywaitlist.dto.SignupResponseDto;
import de.base2code.scripzywaitlist.dto.UserDto;
import de.base2code.scripzywaitlist.response.RecaptchaResponse;
import de.base2code.scripzywaitlist.service.CaptchaService;
import de.base2code.scripzywaitlist.service.EmailDatabase;
import de.base2code.scripzywaitlist.service.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SignUpController {
    private final CaptchaService captchaService;

    @RequestMapping("/signup")
    public String signup(@RequestParam(name="email", required=true) String email,
                         @RequestParam(name="g-recaptcha-response", required = true) String recaptchaResponse,
                         @RequestParam(name="referral", required = false) String referral, Model model) {
        UserDto userDTO = new UserDto();
        userDTO.setEmail(email);
        userDTO.setRecaptchaResponse(recaptchaResponse);
        userDTO.setReferral(referral);
        System.out.println(userDTO);

        try {
            RecaptchaResponse response = captchaService.verify(userDTO.getRecaptchaResponse());

            if (response.isSuccess()) {
                if (!new EmailValidator().isValid(userDTO.getEmail())) {
                    model.addAttribute("error", "Invalid email");
                    return "index";
                }

                if (new EmailDatabase().isEmailInDatabase(userDTO.getEmail())) {
                    model.addAttribute("error", "Email already in database");
                    return "index";
                }

                // TODO: Signup

                model.addAttribute("email", userDTO.getEmail());
                return "signup-ok";
            } else {
                model.addAttribute("error", "general error");
                return "index";
            }
        } catch (InvalidCaptchaException exception) {
            model.addAttribute("error", "Invalid captcha");
            return "index";
        } catch (Exception exception) {
            model.addAttribute("error", "Unknown error");
            return "index";
        }


    }
}
