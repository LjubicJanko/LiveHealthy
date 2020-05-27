package live.healthy.facts.dto;

import live.healthy.facts.model.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class NutritionWrapperDto {
    Set<Food> cereals;
    Set<Food> soups;
    Set<Food> chicken;
    Set<Food> fruits;
    Set<Food> pork;
    Set<Food> pasta;
    Set<Food> eggs;
    Set<Food> snacks;
}
