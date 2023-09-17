package de.base2code.scripzywaitlist.controller;

import de.base2code.scripzywaitlist.config.CaptchaSettings;
import de.base2code.scripzywaitlist.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class IndexController {
    @Autowired
    private CaptchaSettings captchaSettings;

    @GetMapping("/")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        model.addAttribute("recaptchaSiteKey", captchaSettings.getSite());
        return "index";
    }


}
