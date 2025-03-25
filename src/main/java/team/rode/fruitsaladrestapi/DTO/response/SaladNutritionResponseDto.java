package team.rode.fruitsaladrestapi.DTO.response;

import lombok.Data;

@Data
public class SaladNutritionResponseDto {
    private double calories;

    private double fat;

    private double sugar;

    private double carbohydrates;

    private double protein;
}
