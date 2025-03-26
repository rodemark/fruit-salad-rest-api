package team.rode.fruitsaladrestapi.exceptionHandling.exceptions;

public class InvalidProductWeightException extends RuntimeException{
    public InvalidProductWeightException(String message) {
        super(message);
    }
}
