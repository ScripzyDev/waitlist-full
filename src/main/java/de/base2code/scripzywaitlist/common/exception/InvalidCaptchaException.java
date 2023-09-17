package de.base2code.scripzywaitlist.common.exception;

public class InvalidCaptchaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    public InvalidCaptchaException(String message) {
        super(message);
    }
}
