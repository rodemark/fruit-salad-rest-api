package team.rode.fruitsaladrestapi.external.fruitapi.DTO;

import lombok.Data;

@Data
public class FruitResponseDto {
    private Long id;

    private String name;

    private String order;

    private String genus;

    private FruitNutritionResponseDto nutritions;
}
