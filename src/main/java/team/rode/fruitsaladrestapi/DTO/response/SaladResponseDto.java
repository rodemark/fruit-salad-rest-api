package team.rode.fruitsaladrestapi.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class SaladResponseDto {
    private Long id;
    private String name;
    private String description;
    private double totalWeight;
    private double totalCalories;
    private double totalProteins;
    private double totalFats;
    private double totalCarbohydrates;
    private double totalSugar;
    private List<IngredientResponseDto> ingredients;
}
