package team.rode.fruitsaladrestapi.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.fruitsaladrestapi.DTO.request.SaladRequestDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladResponseDto;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.InvalidProductWeightException;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.ResourceNotFoundException;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitNutritionResponseDto;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitResponseDto;
import team.rode.fruitsaladrestapi.external.fruitapi.FruitService;
import team.rode.fruitsaladrestapi.models.NutritionInfo;
import team.rode.fruitsaladrestapi.models.Salad;
import team.rode.fruitsaladrestapi.repositories.SaladRepository;
import team.rode.fruitsaladrestapi.util.CalculationUtil;
import team.rode.fruitsaladrestapi.util.DtoConverter;

import java.util.List;

import static team.rode.fruitsaladrestapi.util.CalculationUtil.BASE_NUTRITION_UNIT;

@Service
public class SaladService {
    private final SaladRepository saladRepository;
    private final DtoConverter dtoConverter;
    private final FruitService fruitService;

    @Autowired
    public SaladService(SaladRepository saladRepository, DtoConverter dtoConverter, FruitService fruitService) {
        this.saladRepository = saladRepository;
        this.dtoConverter = dtoConverter;
        this.fruitService = fruitService;
    }

    protected Salad getSaladById(Long saladId) {
        return saladRepository.findById(saladId)
                .orElseThrow(() -> new ResourceNotFoundException("Salad not found with id: " + saladId));
    }

    public List<SaladResponseDto> getSalads() {
        return saladRepository.findAll().stream()
                .map(salad -> dtoConverter.convertToDto(salad, SaladResponseDto.class)).toList();
    }

    @Transactional
    public SaladResponseDto addSalad(SaladRequestDto saladRequestDto) {
        if (saladRepository.existsByName(saladRequestDto.getName())) {
            throw new DuplicateResourceException("A salad with that name already exists!");
        }

        Salad salad = new Salad();
        saladRequestDtoToSalad(saladRequestDto, salad);

        salad = saladRepository.save(salad);

        return dtoConverter.convertToDto(salad, SaladResponseDto.class);
    }

    @Transactional
    public void deleteSalad(Long saladId) {
        Salad salad = getSaladById(saladId);

        saladRepository.delete(salad);
    }

    @Transactional
    public SaladResponseDto editSalad(SaladRequestDto saladRequestDto, Long saladId) {
        Salad salad = getSaladById(saladId);
        saladRequestDtoToSalad(saladRequestDto, salad);

        salad = saladRepository.save(salad);

        return dtoConverter.convertToDto(salad, SaladResponseDto.class);
    }

    private void saladRequestDtoToSalad(SaladRequestDto saladRequestDto, Salad salad) {
        salad.setName(saladRequestDto.getName());
        salad.setDescription(saladRequestDto.getDescription());

        int totalWeight = 0;
        double totalCalories = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbohydrates = 0;
        double totalSugar = 0;

        for (String nameFruit : saladRequestDto.getSaladRecipe().keySet()) {
            FruitResponseDto fruitResponseDto = fruitService.getFruitByName(nameFruit);

            int currentWeight = saladRequestDto.getSaladRecipe().get(nameFruit);

            if (currentWeight <= 0) {
                throw new InvalidProductWeightException("The weight of the fruit must be positive!");
            }

            FruitNutritionResponseDto nutrition = fruitResponseDto.getNutritions();
            totalWeight += currentWeight;
            totalCalories += nutrition.getCalories() * currentWeight / BASE_NUTRITION_UNIT;
            totalProteins += nutrition.getProtein() * currentWeight / BASE_NUTRITION_UNIT;
            totalFats += nutrition.getFat() * currentWeight / BASE_NUTRITION_UNIT;
            totalCarbohydrates += nutrition.getCarbohydrates() * currentWeight / BASE_NUTRITION_UNIT;
            totalSugar += nutrition.getSugar() * currentWeight / BASE_NUTRITION_UNIT;
        }

        NutritionInfo nutritionInfo = new NutritionInfo();
        nutritionInfo.setTotalWeight(totalWeight);
        nutritionInfo.setTotalCalories(CalculationUtil.round(totalCalories));
        nutritionInfo.setTotalProteins(CalculationUtil.round(totalProteins));
        nutritionInfo.setTotalFats(CalculationUtil.round(totalFats));
        nutritionInfo.setTotalCarbohydrates(CalculationUtil.round(totalCarbohydrates));
        nutritionInfo.setTotalSugar(CalculationUtil.round(totalSugar));

        salad.setNutritionInfo(nutritionInfo);

        salad.setSaladRecipe(saladRequestDto.getSaladRecipe());
    }
}