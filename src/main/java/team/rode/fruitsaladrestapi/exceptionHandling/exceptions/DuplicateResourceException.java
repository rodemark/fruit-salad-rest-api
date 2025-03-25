package team.rode.fruitsaladrestapi.exceptionHandling.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
