package live.healthy.exception.plan;

public class NutritionPlanNotFound extends Exception {
    public NutritionPlanNotFound(Long id) {
        super("Nutrition plan with id " + id + " does not exist.");
    }
}