package team.rode.fruitsaladrestapi.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "salad")
@Getter
@Setter
public class Salad extends BaseEntity{
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private NutritionInfo nutritionInfo;

    @ElementCollection
    @CollectionTable(name = "salad_recipe", joinColumns = @JoinColumn(name = "salad_id"))
    @MapKeyColumn(name = "name_fruit")
    @Column(name = "weight")
    private Map<String, Integer> saladRecipe;
}
