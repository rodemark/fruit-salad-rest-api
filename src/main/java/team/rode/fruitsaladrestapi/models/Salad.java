package team.rode.fruitsaladrestapi.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "salad")
@Getter
@Setter
public class Salad extends BaseEntity{
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "total_weight")
    private double totalWeight;

    @Column(name = "total_calories")
    private double totalCalories;

    @Column(name = "total_proteins")
    private double totalProteins;

    @Column(name = "total_fats")
    private double totalFats;

    @Column(name = "total_carbohydrates")
    private double totalCarbohydrates;

    @Column(name = "total_sugar")
    private double totalSugar;

    @OneToMany(mappedBy = "salad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;
}
