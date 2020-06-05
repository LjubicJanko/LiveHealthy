package live.healthy.facts.model.plan;

import live.healthy.facts.model.food.Food;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class NutritionPlan{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Goal goal;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "weekly_plan",
            joinColumns = @JoinColumn(name = "nutrition_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "daily_nutrition_id"))
    private Set<DailyNutrition> weeklyPlan;


    /*
    * -1 less fat
    * 0 normal amount fat
    * 1 more fat
    * */
    private int fatBased;

    @NotNull
    private double caloriesGoal;

    @NotNull
    private PlanFollowingEnum planFollowingEnum;
}
