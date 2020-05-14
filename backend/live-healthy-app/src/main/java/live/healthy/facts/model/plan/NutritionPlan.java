package live.healthy.facts.model.plan;

import live.healthy.facts.model.food.Food;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class NutritionPlan extends Plan {
    @ManyToMany
    @JoinTable(
            name = "weekly_plan",
            joinColumns = @JoinColumn(name = "nutrition_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "daily_nutrition_id"))
    private Set<DailyNutrition> weeklyPlan;
    @OneToMany
    @JoinTable(
            name = "forbidden_food",
            joinColumns = @JoinColumn(name = "nutrition_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "forbidden_food_id"))
    private Set<Food> forbiddenFood;

}
