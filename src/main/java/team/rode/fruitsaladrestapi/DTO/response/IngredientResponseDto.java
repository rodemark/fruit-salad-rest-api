package team.rode.fruitsaladrestapi.DTO.response;

import lombok.Data;

@Data
public class IngredientResponseDto {
    private Long id;
    private String fruitName;
    private double weight;
    private Long saladId;
}
