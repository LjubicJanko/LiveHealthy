package live.healthy.repository.nutrition;

import live.healthy.facts.model.plan.DailyNutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyNutritionRepository extends JpaRepository<DailyNutrition, Long> {

}
