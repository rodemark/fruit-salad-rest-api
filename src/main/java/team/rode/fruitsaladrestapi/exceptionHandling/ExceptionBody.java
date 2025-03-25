package team.rode.fruitsaladrestapi.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionBody {
    private Integer status;
    private String message;
}
