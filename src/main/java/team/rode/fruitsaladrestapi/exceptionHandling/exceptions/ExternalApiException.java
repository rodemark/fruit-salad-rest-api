package team.rode.fruitsaladrestapi.exceptionHandling.exceptions;

public class ExternalApiException extends RuntimeException{
    public ExternalApiException(String message) {
        super(message);
    }
}
