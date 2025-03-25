package team.rode.fruitsaladrestapi.util;

import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.rode.fruitsaladrestapi.DTO.response.IngredientResponseDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladResponseDto;
import team.rode.fruitsaladrestapi.models.Ingredient;
import team.rode.fruitsaladrestapi.models.Salad;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        configureSalad();
        configureIngredient();
    }

    // Salad -> SaladResponseDto
    private void configureSalad() {
        Converter<List<Ingredient>, List<IngredientResponseDto>> ingredientListConverter =
                new Converter<List<Ingredient>, List<IngredientResponseDto>>() {
                    @Override
                    public List<IngredientResponseDto> convert(MappingContext<List<Ingredient>, List<IngredientResponseDto>> context) {
                        List<Ingredient> sourceList = context.getSource();
                        if (sourceList == null) {
                            return Collections.emptyList();
                        }
                        return sourceList.stream()
                                .map(ingredient -> modelMapper.map(ingredient, IngredientResponseDto.class))
                                .collect(Collectors.toList());
                    }
                };

        modelMapper.addMappings(new PropertyMap<Salad, SaladResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setTotalCarbohydrates(source.getTotalCarbohydrates());
                map().setTotalFats(source.getTotalFats());
                map().setTotalCalories(source.getTotalCalories());
                map().setTotalWeight(source.getTotalWeight());
                map().setTotalProteins(source.getTotalProteins());
                map().setTotalSugar(source.getTotalSugar());

                using(ingredientListConverter).map(source.getIngredients(), destination.getIngredients());
            }
        });
    }

    // Ingredient -> IngredientResponseDto
    private void configureIngredient() {
        modelMapper.addMappings(new PropertyMap<Ingredient, IngredientResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setSaladId(source.getSalad().getId());
                map().setWeight(source.getWeight());
                map().setFruitName(source.getFruitName());
            }
        });
    }


    public <D, T> D convertToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <D, T> T convertToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
