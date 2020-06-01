package live.healthy.facts.dto;

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
}
