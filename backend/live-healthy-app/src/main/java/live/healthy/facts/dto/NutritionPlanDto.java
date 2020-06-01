package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionPlanDto {

    @NotNull
    private Long id;
    @NotNull
    private String goal;
    @NotNull
    private Set<DailyNutritionDto> weeklyPlan;
    @NotNull
    private Set<FoodDto> forbiddenFood;
    @NotNull
    private int fatBased;
    @NotNull
    private double caloriesGoal;
}
