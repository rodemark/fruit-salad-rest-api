package team.rode.fruitsaladrestapi.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

@Data
public class SaladRequestDto {
    @NotBlank(message = "Name is required!")
    private String name;

    private String description;

    @NotEmpty(message = "A salad recipe cannot be empty!")
    private Map<String, Integer> saladRecipe;
}
