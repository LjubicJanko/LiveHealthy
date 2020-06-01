package live.healthy.service.plan;

import live.healthy.exception.plan.NutritionPlanAlreadyExists;
import live.healthy.exception.plan.NutritionPlanNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.CreatePlanInfoDto;
import live.healthy.facts.dto.IllnessesDto;
import live.healthy.facts.dto.NutritionAndTrainingDto;
import live.healthy.facts.dto.NutritionPlanDto;

import java.util.List;
public interface PlanService {

    NutritionAndTrainingDto createPlan(Long userId, CreatePlanInfoDto createPlanInfoDto) throws UserNotFound, NutritionPlanAlreadyExists;

    NutritionPlanDto getNutritionPlan(Long id) throws NutritionPlanNotFound;
}
