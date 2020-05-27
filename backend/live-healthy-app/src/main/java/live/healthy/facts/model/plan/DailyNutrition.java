package live.healthy.facts.model.plan;

import live.healthy.facts.model.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyNutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "daily_food",
            joinColumns = @JoinColumn(name = "daily_nutrition_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private Set<Food> dailyFood;
    @ManyToMany
    @JoinTable(
            name = "daily_snacks",
            joinColumns = @JoinColumn(name = "daily_nutrition_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private Set<Food> snacks;



}
