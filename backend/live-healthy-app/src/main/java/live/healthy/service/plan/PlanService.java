package live.healthy.service.plan;

import live.healthy.exception.plan.NutritionPlanAlreadyExists;
import live.healthy.exception.plan.NutritionPlanNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.NutritionPlanDto;

public interface PlanService {

    /**
     * Method used to create nutrition plan for one week
     *
     * @param userId
     * @return
     * @throws UserNotFound
     * @throws NutritionPlanAlreadyExists
     */
    NutritionPlanDto createPlan(Long userId) throws UserNotFound, NutritionPlanAlreadyExists;

    /**
     * Method used to fetch nutrition plan by its id
     *
     * @param id
     * @return
     * @throws NutritionPlanNotFound
     */
    NutritionPlanDto getNutritionPlan(Long id) throws NutritionPlanNotFound;
}
