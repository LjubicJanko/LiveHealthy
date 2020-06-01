package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionAndTrainingDto {

    private NutritionPlanDto nutritionPlanDto;
    private TrainingPlanDto trainingPlanDto;
}
