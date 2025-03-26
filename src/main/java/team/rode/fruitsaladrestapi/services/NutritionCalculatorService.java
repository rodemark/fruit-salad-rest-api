package team.rode.fruitsaladrestapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.fruitsaladrestapi.DTO.response.SaladNutritionResponseDto;
import team.rode.fruitsaladrestapi.models.Salad;
import team.rode.fruitsaladrestapi.util.CalculationUtil;

import static team.rode.fruitsaladrestapi.util.CalculationUtil.BASE_NUTRITION_UNIT;

@Service
public class NutritionCalculatorService {
    private final SaladService saladService;

    @Autowired
    public NutritionCalculatorService(SaladService saladService) {
        this.saladService = saladService;
    }

    public SaladNutritionResponseDto calculateNutritionPer100g(Long saladId) {
        Salad salad = saladService.getSaladById(saladId);
        double totalWeight = salad.getNutritionInfo().getTotalWeight();
        SaladNutritionResponseDto nutrition = new SaladNutritionResponseDto();

        double calories = salad.getNutritionInfo().getTotalCalories() / totalWeight * BASE_NUTRITION_UNIT;
        double fat = salad.getNutritionInfo().getTotalFats() / totalWeight * BASE_NUTRITION_UNIT;
        double sugar = salad.getNutritionInfo().getTotalSugar() / totalWeight * BASE_NUTRITION_UNIT;
        double carbohydrates = salad.getNutritionInfo().getTotalCarbohydrates() / totalWeight * BASE_NUTRITION_UNIT;
        double protein = salad.getNutritionInfo().getTotalProteins() / totalWeight * BASE_NUTRITION_UNIT;

        nutrition.setCalories(CalculationUtil.round(calories));
        nutrition.setFat(CalculationUtil.round(fat));
        nutrition.setSugar(CalculationUtil.round(sugar));
        nutrition.setCarbohydrates(CalculationUtil.round(carbohydrates));
        nutrition.setProtein(CalculationUtil.round(protein));

        nutrition.setSaladId(saladId);
        nutrition.setSaladName(salad.getName());

        return nutrition;
    }
}
