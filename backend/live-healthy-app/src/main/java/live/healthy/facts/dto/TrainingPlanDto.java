package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPlanDto {

    // Todo: add validation annotations through all subdtos
    public Long id;
    public String goal;
    private Set<DailyTrainingDto> weeklyPlan;
    private Set<ExerciseDto> forbiddenExercises;

}
