package de.base2code.scripzywaitlist.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    public boolean isValid(final String mail) {
        return (validateEmail(mail));
    }

    private boolean validateEmail(final String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}
