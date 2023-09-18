package de.base2code.scripzywaitlist.controller;

import de.base2code.scripzywaitlist.dto.SubscribedUserDto;
import de.base2code.scripzywaitlist.service.UserDatabase;
import de.base2code.scripzywaitlist.utils.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Controller
public class TokenController {
    @Autowired
    private UserDatabase userDatabase;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @RequestMapping("/token")
    public String validateEmailToken(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "email") String email,
            Model model) {
        SubscribedUserDto user = userDatabase.getUserByEmail(email);
        if (user == null) {
            log.info("User with email " + email + " not found");
            model.addAttribute("error", "Ungültige Anfrage!");
            return "index";
        }
        if (!token.equalsIgnoreCase(user.getToken())) {
            log.info("Invalid token for user " + email);
            model.addAttribute("error", "Ungültige Anfrage!");
            return "index";
        }
        if (user.isActive()) {
            log.info("User " + email + " already activated");
            model.addAttribute("error", "Du hast deine E-Mail bereits aktiviert.");
            return "index";
        }

        user.setActive(true);
        userDatabase.save(user);

        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String body = templateEngine.process("actiavtion-email-sent", context);
        mailClient.sendEmail(email, "Test", body);

        model.addAttribute("email", email);
        return "verify-successful";
    }

}
