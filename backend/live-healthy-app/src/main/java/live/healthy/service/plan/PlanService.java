package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;

public interface PlanService {

    void createPlan(Long userId) throws UserNotFound;
}
