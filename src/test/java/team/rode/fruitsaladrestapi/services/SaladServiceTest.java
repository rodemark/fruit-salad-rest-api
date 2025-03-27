package team.rode.fruitsaladrestapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import team.rode.fruitsaladrestapi.DTO.request.SaladRequestDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladResponseDto;
import team.rode.fruitsaladrestapi.exceptionHandling.exceptions.InvalidProductWeightException;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitNutritionResponseDto;
import team.rode.fruitsaladrestapi.external.fruitapi.DTO.FruitResponseDto;
import team.rode.fruitsaladrestapi.external.fruitapi.FruitService;
import team.rode.fruitsaladrestapi.models.NutritionInfo;
import team.rode.fruitsaladrestapi.models.Salad;
import team.rode.fruitsaladrestapi.repositories.SaladRepository;
import team.rode.fruitsaladrestapi.util.DtoConverter;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
class SaladServiceTest {

    @Mock
    private SaladRepository saladRepository;

    @Mock
    private DtoConverter dtoConverter;

    @Mock
    private FruitService fruitService;

    @InjectMocks
    private SaladService saladService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findDuplicateSalads_returnsOneGroup_whenTwoSaladsHaveSameRecipe() {
        Salad salad1 = new Salad();
        salad1.setId(1L);
        salad1.setName("Salad One");
        Map<String, Integer> recipe = new HashMap<>();
        recipe.put("apple", 100);
        recipe.put("banana", 50);
        salad1.setSaladRecipe(recipe);

        Salad salad2 = new Salad();
        salad2.setId(2L);
        salad2.setName("Salad Two");
        salad2.setSaladRecipe(new HashMap<>(recipe));

        List<Salad> salads = Arrays.asList(salad1, salad2);
        when(saladRepository.findAll()).thenReturn(salads);

        SaladResponseDto dto1 = new SaladResponseDto();
        dto1.setId(salad1.getId());
        dto1.setName(salad1.getName());

        SaladResponseDto dto2 = new SaladResponseDto();
        dto2.setId(salad2.getId());
        dto2.setName(salad2.getName());

        when(dtoConverter.convertToDto(salad1, SaladResponseDto.class)).thenReturn(dto1);
        when(dtoConverter.convertToDto(salad2, SaladResponseDto.class)).thenReturn(dto2);

        List<List<SaladResponseDto>> duplicates = saladService.findDuplicateSalads();

        assertEquals(1, duplicates.size(), "There should be one group of duplicates!");
        List<SaladResponseDto> group = duplicates.getFirst();
        assertEquals(2, group.size(), "The duplicate group should contain two salads!");
    }

    @Test
    void findDuplicateSalads_returnsEmptyList_whenNoDuplicates() {
        Salad salad1 = new Salad();
        salad1.setId(1L);
        salad1.setName("Salad One");
        Map<String, Integer> recipe1 = new HashMap<>();
        recipe1.put("apple", 100);
        salad1.setSaladRecipe(recipe1);

        Salad salad2 = new Salad();
        salad2.setId(2L);
        salad2.setName("Salad Two");
        Map<String, Integer> recipe2 = new HashMap<>();
        recipe2.put("banana", 50);
        salad2.setSaladRecipe(recipe2);

        List<Salad> salads = Arrays.asList(salad1, salad2);
        when(saladRepository.findAll()).thenReturn(salads);

        SaladResponseDto dto1 = new SaladResponseDto();
        dto1.setId(salad1.getId());
        dto1.setName(salad1.getName());

        SaladResponseDto dto2 = new SaladResponseDto();
        dto2.setId(salad2.getId());
        dto2.setName(salad2.getName());

        when(dtoConverter.convertToDto(salad1, SaladResponseDto.class)).thenReturn(dto1);
        when(dtoConverter.convertToDto(salad2, SaladResponseDto.class)).thenReturn(dto2);

        List<List<SaladResponseDto>> duplicates = saladService.findDuplicateSalads();

        assertTrue(duplicates.isEmpty(), "There should be no duplicate groups if the recipes are different!");
    }

    @Test
    public void testAddSalad_TotalWeightPositive() {
        SaladRequestDto requestDto = new SaladRequestDto();
        requestDto.setName("Test salad");
        requestDto.setDescription("Salad for testing");

        Map<String, Integer> recipe = new HashMap<>();
        recipe.put("Apple", 150);
        recipe.put("Banana", 100);
        requestDto.setSaladRecipe(recipe);

        FruitNutritionResponseDto appleNutrition = new FruitNutritionResponseDto();
        appleNutrition.setCalories(52);
        appleNutrition.setProtein(0.3);
        appleNutrition.setFat(0.2);
        appleNutrition.setCarbohydrates(14);
        appleNutrition.setSugar(10);
        FruitResponseDto appleResponse = new FruitResponseDto();
        appleResponse.setName("Apple");
        appleResponse.setNutritions(appleNutrition);

        FruitNutritionResponseDto bananaNutrition = new FruitNutritionResponseDto();
        bananaNutrition.setCalories(96);
        bananaNutrition.setProtein(1.3);
        bananaNutrition.setFat(0.3);
        bananaNutrition.setCarbohydrates(27);
        bananaNutrition.setSugar(14);
        FruitResponseDto bananaResponse = new FruitResponseDto();
        bananaResponse.setName("Banana");
        bananaResponse.setNutritions(bananaNutrition);

        when(fruitService.getFruitByName("Apple")).thenReturn(appleResponse);
        when(fruitService.getFruitByName("Banana")).thenReturn(bananaResponse);

        when(saladRepository.save(any(Salad.class))).thenAnswer(invocation -> {
            Salad salad = invocation.getArgument(0);
            salad.setId(1L);
            return salad;
        });

        when(dtoConverter.convertToDto(any(Salad.class), eq(SaladResponseDto.class))).thenAnswer(invocation -> {
            Salad salad = invocation.getArgument(0);
            SaladResponseDto dto = new SaladResponseDto();
            dto.setId(salad.getId());
            dto.setName(salad.getName());
            dto.setNutritionInfo(salad.getNutritionInfo());
            return dto;
        });

        SaladResponseDto responseDto = saladService.addSalad(requestDto);
        NutritionInfo nutritionInfo = responseDto.getNutritionInfo();

        assertNotNull(nutritionInfo, "NutritionInfo not must be null!");
        assertTrue(nutritionInfo.getTotalWeight() > 0, "The total weight of the salad should be more than 0!");
    }

    @Test
    public void testAddSalad_InvalidFruitWeight() {
        SaladRequestDto requestDto = new SaladRequestDto();
        requestDto.setName("wrong salad");
        requestDto.setDescription("test negative weight");

        Map<String, Integer> recipe = new HashMap<>();
        recipe.put("Apple", -50);
        requestDto.setSaladRecipe(recipe);

        when(fruitService.getFruitByName("Apple")).thenReturn(new FruitResponseDto());

        Exception exception = assertThrows(InvalidProductWeightException.class, () -> saladService.addSalad(requestDto));
        assertEquals("The weight of the fruit must be positive!", exception.getMessage());
    }
}
