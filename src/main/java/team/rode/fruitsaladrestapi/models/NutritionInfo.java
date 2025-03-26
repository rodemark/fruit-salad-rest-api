package team.rode.fruitsaladrestapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class NutritionInfo {

    @Column(name = "total_weight")
    private int totalWeight;

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
}
