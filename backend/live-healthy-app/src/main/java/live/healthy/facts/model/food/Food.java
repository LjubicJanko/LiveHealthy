package live.healthy.facts.model.food;

import live.healthy.facts.model.plan.NutritionPlan;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String kcal_100g;

    @ManyToMany
    private Set<NutritionPlan> nutritionPlans;
}
