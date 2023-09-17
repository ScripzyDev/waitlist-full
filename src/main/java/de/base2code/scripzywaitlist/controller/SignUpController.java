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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SignUpController {
    private final CaptchaService captchaService;

    @Autowired
    private EmailDatabase emailDatabase;

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
                    model.addAttribute("error", "Ungültige Email");
                    log.info("Invalid email: " + userDTO.getEmail());
                    return "index";
                }

                if (emailDatabase.isEmailInDatabase(userDTO.getEmail())) {
                    model.addAttribute("error", "Du bist bereits auf der Warteliste");
                    log.info("Email already in database: " + userDTO.getEmail());
                    return "index";
                }

                if (!emailDatabase.addEmailToDatabase(userDTO.getEmail(), userDTO.getReferral())) {
                    model.addAttribute("error", "Es ist ein Fehler aufgetreten (-1)");
                    log.info("Could not add email to database: " + userDTO.getEmail());
                    return "index";
                }

                model.addAttribute("email", userDTO.getEmail());
                return "signup-ok";
            } else {
                model.addAttribute("error", "Es ist ein Fehler aufgetreten (-2)");
                log.info("General error: " + userDTO.getEmail());
                return "index";
            }
        } catch (InvalidCaptchaException exception) {
            model.addAttribute("error", "Es ist ein Fehler aufgetreten (-3)");
            log.info("Invalid captcha: " + userDTO.getEmail() + "; " + exception.getMessage());
            return "index";
        } catch (Exception exception) {
            exception.printStackTrace();
            model.addAttribute("error", "Es ist ein Fehler aufgetreten (-4)");
            log.info("Unknown error: " + userDTO.getEmail() + "; " + exception.getMessage());
            return "index";
        }


    }
}
