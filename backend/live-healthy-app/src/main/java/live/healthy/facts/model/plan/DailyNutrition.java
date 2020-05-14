package live.healthy.facts.model.plan;

import live.healthy.facts.model.food.Food;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class DailyNutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "breakfast_food",
            joinColumns = @JoinColumn(name = "daily_nutrition_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private Set<Food> breakfast;
    @ManyToMany
    @JoinTable(
            name = "lunch_food",
            joinColumns = @JoinColumn(name = "daily_nutrition_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private Set<Food> lunch;
    @ManyToMany
    @JoinTable(
            name = "dinner_food",
            joinColumns = @JoinColumn(name = "daily_nutrition_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private Set<Food> dinner;
    @ManyToMany
    @JoinTable(
            name = "snacks_food",
            joinColumns = @JoinColumn(name = "snacks_nutrition_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private Set<Food> snacks;

    @NotNull
    private double caloriesGoal;

    @ManyToMany
    private Set<NutritionPlan> nutritionPlans;

}
