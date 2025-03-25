package team.rode.fruitsaladrestapi.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SaladRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotEmpty(message = "Ingredients list cannot be empty")
    private List<IngredientRequestDto> ingredients;
}
