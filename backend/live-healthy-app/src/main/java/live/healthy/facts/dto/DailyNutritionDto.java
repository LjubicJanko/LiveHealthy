package live.healthy.facts.dto;

import live.healthy.events.IntakeSubmitEvent;
import live.healthy.events.IntakeSubmitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyNutritionDto {

    @NotNull
    private Set<FoodDto> dailyFood;
    @Nullable
    private Set<FoodDto> snacks;
    @NotNull
    private int dayOfTheWeek;

    private IntakeSubmitType intakeSubmitType;
}
