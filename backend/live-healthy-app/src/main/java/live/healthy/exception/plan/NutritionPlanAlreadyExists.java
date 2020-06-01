package live.healthy.exception.plan;

public class NutritionPlanAlreadyExists extends Exception {
    public NutritionPlanAlreadyExists(Long userId) {
        super("User with id " + userId + " already has nutrition plan");
    }
}
