package team.rode.fruitsaladrestapi.DTO.response;

import lombok.Data;
import team.rode.fruitsaladrestapi.models.NutritionInfo;

import java.util.Map;

@Data
public class SaladResponseDto {
    private Long id;
    private String name;
    private String description;
    private NutritionInfo nutritionInfo;
    private Map<String, Integer> saladRecipe;
}
