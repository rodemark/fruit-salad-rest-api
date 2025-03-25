package team.rode.fruitsaladrestapi.DTO.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IngredientRequestDto {
    @NotBlank(message = "Fruit name is required")
    private String fruitName;

    @NotNull(message = "Weight is not null")
    private double weight;
}
