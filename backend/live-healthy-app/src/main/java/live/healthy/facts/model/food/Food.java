package live.healthy.facts.model.food;

import live.healthy.facts.model.plan.NutritionPlan;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotBlank
//    private String name;
//    @NotBlank
//    private String kcal_100g;

    @NotBlank
    private String foodGroup;
    @NotBlank
    private String description;
    @NotNull
    private int energy_kcal;
    @NotNull
    private int proteins;
    @NotNull
    private double fats;
    @NotNull
    private double carbs;

    @ManyToMany
    private Set<NutritionPlan> nutritionPlans;
}
