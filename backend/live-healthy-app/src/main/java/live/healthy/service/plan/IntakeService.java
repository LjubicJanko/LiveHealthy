package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.NutritionPlanDto;

public interface IntakeService {

    /**
     * Method used to submit users intake information for one day
     *
     * @param userId
     * @param dayIndex
     * @param caloriesDifference
     */
    NutritionPlanDto submit(Long userId, int dayIndex, double caloriesDifference) throws UserNotFound;
}
