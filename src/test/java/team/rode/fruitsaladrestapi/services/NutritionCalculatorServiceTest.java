package team.rode.fruitsaladrestapi.services;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import team.rode.fruitsaladrestapi.DTO.response.SaladNutritionResponseDto;
import team.rode.fruitsaladrestapi.models.NutritionInfo;
import team.rode.fruitsaladrestapi.models.Salad;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
class NutritionCalculatorServiceTest {

    @Mock
    private SaladService saladService;

    @InjectMocks
    private NutritionCalculatorService nutritionCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateNutritionPer100g_returnsCorrectValues() {
        Long saladId = 1L;
        Salad salad = getSalad(saladId);

        when(saladService.getSaladById(saladId)).thenReturn(salad);

        SaladNutritionResponseDto result = nutritionCalculatorService.calculateNutritionPer100g(saladId);

        // Calculation of expected values:
        // calories: (400 / 200) * 100 = 200.0
        // proteins: (20 / 200) * 100 = 10.0
        // fats: (10 / 200) * 100 = 5.0
        // carbohydrates: (50 / 200) * 100 = 25.0
        // sugar: (30 / 200) * 100 = 15.0

        assertEquals(200.0, result.getCalories(), "Calories per 100g should be 200.0");
        assertEquals(10.0, result.getProtein(), "Proteins per 100g should be 10.0");
        assertEquals(5.0, result.getFat(), "Fats per 100g should be 5.0");
        assertEquals(25.0, result.getCarbohydrates(), "Carbohydrates per 100g should be 25.0");
        assertEquals(15.0, result.getSugar(), "Sugar per 100g should be 15.0");
        assertEquals(saladId, result.getSaladId(), "Salad ID should match");
        assertEquals("Test Salad", result.getSaladName(), "Salad name should match!");
    }

    @NotNull
    private static Salad getSalad(Long saladId) {
        Salad salad = new Salad();
        salad.setId(saladId);
        salad.setName("Test Salad");

        NutritionInfo info = new NutritionInfo();
        info.setTotalWeight(200);
        info.setTotalCalories(400);
        info.setTotalProteins(20);
        info.setTotalFats(10);
        info.setTotalCarbohydrates(50);
        info.setTotalSugar(30);
        salad.setNutritionInfo(info);

        return salad;
    }
}

