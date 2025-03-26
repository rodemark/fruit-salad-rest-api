package team.rode.fruitsaladrestapi.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.ExternalApiException;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.InvalidProductWeightException;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.ResourceNotFoundException;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionBody(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleDuplicateResourceException(DuplicateResourceException e) {
        return new ExceptionBody(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleValidationExceptions(MethodArgumentNotValidException e) {
        return new ExceptionBody(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionBody handleExternalApiException(ExternalApiException e) {
        return new ExceptionBody(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidProductWeightException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleInvalidProductWeightException(InvalidProductWeightException e) {
        return new ExceptionBody(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
