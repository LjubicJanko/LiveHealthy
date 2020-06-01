package live.healthy.repository.nutrition;

import live.healthy.facts.model.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Set<Food> findAllByFoodGroup(String s);

    Set<Food> findAllByDescription(Set<String> forbiddenFood);
}
