package live.healthy.repository.plan;

import live.healthy.facts.model.plan.NutritionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
}
