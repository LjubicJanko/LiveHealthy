package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;
import java.util.List;
public interface PlanService {

    void createPlan(Long userId, List<String> forbiddenFood) throws UserNotFound;
}
