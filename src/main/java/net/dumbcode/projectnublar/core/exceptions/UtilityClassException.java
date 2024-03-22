package net.dumbcode.projectnublar.core.exceptions;

public class UtilityClassException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "This is a utility class and cannot be instantiated";

    public UtilityClassException() {
        super(DEFAULT_MESSAGE);
    }

    public UtilityClassException(String message) {
        super(message);
    }

    public UtilityClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilityClassException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public UtilityClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UtilityClassException(Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(DEFAULT_MESSAGE, cause, enableSuppression, writableStackTrace);
    }

}
