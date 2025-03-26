package team.rode.fruitsaladrestapi.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.fruitsaladrestapi.DTO.request.IngredientRequestDto;
import team.rode.fruitsaladrestapi.DTO.request.SaladRequestDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladNutritionResponseDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladResponseDto;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.ResourceNotFoundException;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitNutritionResponseDto;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitResponseDto;
import team.rode.fruitsaladrestapi.external.fruitapi.FruitService;
import team.rode.fruitsaladrestapi.models.Ingredient;
import team.rode.fruitsaladrestapi.models.Salad;
import team.rode.fruitsaladrestapi.repositories.SaladRepository;
import team.rode.fruitsaladrestapi.util.DtoConverter;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaladService {
    private final SaladRepository saladRepository;
    private final DtoConverter dtoConverter;
    private final FruitService fruitService;

    private static final double BASE_NUTRITION_UNIT = 100.0;

    @Autowired
    public SaladService(SaladRepository saladRepository, DtoConverter dtoConverter, FruitService fruitService) {
        this.saladRepository = saladRepository;
        this.dtoConverter = dtoConverter;
        this.fruitService = fruitService;
    }

    private Salad getSaladByName(String name) {
        return saladRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Salad not found with name: " + name));
    }

    public List<SaladResponseDto> getSalads() {
        return saladRepository.findAll().stream()
                .map(salad -> dtoConverter.convertToDto(salad, SaladResponseDto.class)).toList();
    }

    public SaladNutritionResponseDto calculateNutritionPer100g(String saladName) {
        Salad salad = getSaladByName(saladName);
        double totalWeight = salad.getTotalWeight();
        SaladNutritionResponseDto nutrition = new SaladNutritionResponseDto();

        nutrition.setCalories(salad.getTotalCalories() / totalWeight * BASE_NUTRITION_UNIT);
        nutrition.setFat(salad.getTotalFats() / totalWeight * BASE_NUTRITION_UNIT);
        nutrition.setSugar(salad.getTotalSugar() / totalWeight * BASE_NUTRITION_UNIT);
        nutrition.setCarbohydrates(salad.getTotalCarbohydrates() / totalWeight * BASE_NUTRITION_UNIT);

        return nutrition;
    }

    @Transactional
    public SaladResponseDto addSalad(SaladRequestDto saladRequestDto) {
        if (saladRepository.existsByName(saladRequestDto.getName())) {
            throw new DuplicateResourceException("A salad with that name already exists!");
        }

        Salad salad = new Salad();

        salad.setName(saladRequestDto.getName());
        salad.setDescription(saladRequestDto.getDescription());

        double totalWeight = 0;
        double totalCalories = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbohydrates = 0;
        double totalSugar = 0;

        List<Ingredient> ingredients = new ArrayList<>();

        List<IngredientRequestDto> ingredientRequestDtoList = saladRequestDto.getIngredients();
        for (IngredientRequestDto ingredientRequestDto : ingredientRequestDtoList) {
            FruitResponseDto fruitResponseDto = fruitService.getFruitByName(ingredientRequestDto.getFruitName());

            double currentWeight = ingredientRequestDto.getWeight();

            FruitNutritionResponseDto nutrition = fruitResponseDto.getNutritions();
            totalWeight += currentWeight;
            totalCalories += nutrition.getCalories() * currentWeight / BASE_NUTRITION_UNIT;
            totalProteins += nutrition.getProtein() * currentWeight / BASE_NUTRITION_UNIT;
            totalFats += nutrition.getFat() * currentWeight / BASE_NUTRITION_UNIT;
            totalCarbohydrates += nutrition.getCarbohydrates() * currentWeight / BASE_NUTRITION_UNIT;
            totalSugar += nutrition.getSugar() * currentWeight / BASE_NUTRITION_UNIT;

            Ingredient ingredient = new Ingredient();
            ingredient.setFruitName(ingredientRequestDto.getFruitName());
            ingredient.setWeight(currentWeight);
            ingredient.setSalad(salad);

            ingredients.add(ingredient);
        }

        salad.setIngredients(ingredients);
        salad.setTotalWeight(totalWeight);
        salad.setTotalCalories(totalCalories);
        salad.setTotalProteins(totalProteins);
        salad.setTotalFats(totalFats);
        salad.setTotalCarbohydrates(totalCarbohydrates);
        salad.setTotalSugar(totalSugar);

        salad = saladRepository.save(salad);

        return dtoConverter.convertToDto(salad, SaladResponseDto.class);
    }

}