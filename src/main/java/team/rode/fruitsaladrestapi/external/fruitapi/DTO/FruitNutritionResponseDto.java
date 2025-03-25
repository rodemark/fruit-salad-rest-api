package team.rode.fruitsaladrestapi.external.fruitapi.DTO;

import lombok.Data;

@Data
public class FruitNutritionResponseDto {
    private double calories;

    private double fat;

    private double sugar;

    private double carbohydrates;

    private double protein;
}
