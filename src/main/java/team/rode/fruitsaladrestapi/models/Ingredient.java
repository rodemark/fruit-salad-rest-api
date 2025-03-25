package team.rode.fruitsaladrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
public class Ingredient extends BaseEntity{
    @Column(name = "fruit_name")
    private String fruitName;

    @Column(name = "weight")
    private double weight;

    @ManyToOne
    @JoinColumn(name = "salad_id")
    private Salad salad;
}
