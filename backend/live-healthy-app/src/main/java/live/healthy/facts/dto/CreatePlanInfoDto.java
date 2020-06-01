package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanInfoDto {

    private ForbiddenFoodDto forbiddenFoodDto;

    private IllnessesDto illnessesDto;
}
