package team.rode.fruitsaladrestapi.exceptionHandling.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
