package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;

public interface IntakeService {

    /**
     * Method used to submit users intake information for one day
     *
     * @param userId
     * @param dayIndex
     * @param caloriesDifference
     */
    void submit(Long userId, int dayIndex, double caloriesDifference) throws UserNotFound;
}
