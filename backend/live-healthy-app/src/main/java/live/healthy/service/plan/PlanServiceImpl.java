package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.NutritionWrapperDto;
import live.healthy.facts.dto.PlanDto;
import live.healthy.facts.model.food.Food;
import live.healthy.facts.model.plan.DailyNutrition;
import live.healthy.facts.model.plan.Goal;
import live.healthy.facts.model.plan.NutritionPlan;
import live.healthy.facts.model.user.User;
import live.healthy.repository.UserRepository;
import live.healthy.repository.nutrition.DailyNutritionRepository;
import live.healthy.repository.nutrition.FoodRepository;
import live.healthy.repository.plan.NutritionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private static Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);


    private final UserRepository userRepository;
    private final KieContainer kieContainer;
    private final FoodRepository foodRepository;
    private final DailyNutritionRepository dailyNutritionRepository;
    private final NutritionPlanRepository nutritionPlanRepository;

//    @Autowired
//    public PlanServiceImpl(KieContainer kieContainer, UserRepository userRepository) {
//        log.info("Initialising a new creatingPlan session.");
//        this.kieContainer = kieContainer;
//        this.userRepository = userRepository;
//    }


    @Override
    public void createPlan(Long userId, List<String> forbiddenFood) throws UserNotFound {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        KieSession kieSession = kieContainer.newKieSession("creatingPlan");

        kieSession.setGlobal("bodyType", user.getBodyType().getType());
        PlanDto planDto = new PlanDto("", 0, 0.0);

        kieSession.insert(planDto);
        kieSession.insert(user);


        int i = kieSession.fireAllRules();
        kieSession.dispose();
        createWeeklyNutrition(planDto, forbiddenFood, user);
    }

    private void createWeeklyNutrition(PlanDto planDto, List<String> forbiddenFood, User user) {


        KieSession kieSession = kieContainer.newKieSession("creatingNutritionPlan");

        kieSession.insert(user);
        kieSession.insert(planDto);

        int i = kieSession.fireAllRules();
        kieSession.dispose();


        double calorieLimit = user.getStartingBmr() * planDto.getBmrPercentNeeded();

        NutritionPlan nutritionPlan = new NutritionPlan();
        nutritionPlan.setGoal(Goal.valueOf(planDto.getGoal()));
        nutritionPlan.setWeeklyPlan(findWeeklyFoodPlan(nutritionPlan.getGoal(), calorieLimit,
                planDto.getFatLevelNeeded(), forbiddenFood));
        nutritionPlan.setCaloriesGoal(calorieLimit);
        nutritionPlan.setFatBased(planDto.fatLevelNeeded);
        nutritionPlan.setForbiddenFood(getForbiddenFood(forbiddenFood));

        user.setNutritionPlan(nutritionPlanRepository.save(nutritionPlan));
        userRepository.save(user);
    }

    private Set<Food> getForbiddenFood(List<String> forbiddenFood) {
        Set<Food> forbidden = foodRepository.findAllByDescription(forbiddenFood);
        return forbidden;
    }

    private Set<DailyNutrition> findWeeklyFoodPlan(Goal goal, double calorieLimit, int fatLevel, List<String> forbiddenFood) {
        Set<Food> cereals = foodRepository.findAllByFoodGroup("Breakfast Cereals");
        Set<Food> soups = foodRepository.findAllByFoodGroup("Soups- Sauces- and Gravies");
        Set<Food> chicken = foodRepository.findAllByFoodGroup("Poultry Products");
        Set<Food> fruits = foodRepository.findAllByFoodGroup("Fruits and Fruit Juices");
        Set<Food> pork = foodRepository.findAllByFoodGroup("Pork Products");
        Set<Food> pasta = foodRepository.findAllByFoodGroup("Cereal Grains and Pasta");
        Set<Food> eggs = foodRepository.findAllByFoodGroup("Dairy and Egg Products");
        Set<Food> snacks = foodRepository.findAllByFoodGroup("Snacks");

        NutritionWrapperDto nutritionWrapperDto = new NutritionWrapperDto(cereals, soups, chicken, fruits, pork,
                pasta, eggs, snacks);

        Set<DailyNutrition> weeklyNutrition = getWeeklyNutrition(goal, nutritionWrapperDto, fatLevel, calorieLimit,
                forbiddenFood);

        return weeklyNutrition;
    }

    private Set<DailyNutrition> getWeeklyNutrition(Goal goal, NutritionWrapperDto nutritionWrapperDto,
                                                   int fatLevel, double calorieLimit, List<String> forbiddenFood) {
        Food breakfast, lunch, dinner, snack, snacks_fruit = new Food();

        double breakfastCalories = calorieLimit / 3;
        double lunchCalories = calorieLimit / 2;
        double dinnerCalories = calorieLimit - breakfastCalories - lunchCalories;
        double snackCalories = 0;

        List<Food> choosenFood = new ArrayList<>();
        Dictionary week = new Hashtable();
        week.put(0, new DailyNutrition());
        week.put(1, new DailyNutrition());
        week.put(2, new DailyNutrition());
        week.put(3, new DailyNutrition());
        week.put(4, new DailyNutrition());
        week.put(5, new DailyNutrition());
        week.put(6, new DailyNutrition());

        for (int i = 0; i < 7; i++) {

            Set<Food> dailyFood = new HashSet<>();
            Set<Food> dailySnacks = new HashSet<>();

            switch (goal) {
                case WEIGHT_LOSS:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getCereals(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getFruits(), forbiddenFood);
                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case MILD_WEIGHT_LOSS:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getFruits(), forbiddenFood);
                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case EXTREME_WEIGHT_LOSS:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    choosenFood = Arrays.asList(breakfast, lunch);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case MAINTAIN_WEIGHT:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta(), forbiddenFood);
                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case WEIGHT_GAIN:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta(), forbiddenFood);
                    snackCalories = calorieLimit - breakfast.getEnergy_kcal() -
                            lunch.getEnergy_kcal() - dinner.getEnergy_kcal();
                    snack = findSingleFood(snackCalories, fatLevel, nutritionWrapperDto.getSnacks(), forbiddenFood);

                    choosenFood = Arrays.asList(breakfast, lunch, dinner);
                    dailySnacks.add(snack);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case MILD_WEIGHT_GAIN:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getCereals(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta(), forbiddenFood);
                    snackCalories = calorieLimit - breakfast.getEnergy_kcal() -
                            lunch.getEnergy_kcal() - dinner.getEnergy_kcal();
                    snack = findSingleFood(snackCalories, fatLevel, nutritionWrapperDto.getSnacks(), forbiddenFood);
                    dailySnacks.add(snack);

                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case EXTREME_WEIGHT_GAIN:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getCereals(), forbiddenFood);
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken(), forbiddenFood);
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta(), forbiddenFood);
                    snackCalories = calorieLimit - breakfast.getEnergy_kcal() -
                            lunch.getEnergy_kcal() - dinner.getEnergy_kcal();
                    snack = findSingleFood(snackCalories / 2, fatLevel, nutritionWrapperDto.getSnacks(), forbiddenFood);
                    snacks_fruit = findSingleFood(snackCalories / 2, fatLevel, nutritionWrapperDto.getFruits(), forbiddenFood);
                    dailySnacks.addAll(Arrays.asList(snack, snacks_fruit));

                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                default:
                    break;
            }

            DailyNutrition dailyNutrition = new DailyNutrition();
            dailyNutrition.setDailyFood(dailyFood);
            dailyNutrition.setSnacks(dailySnacks);

            dailyNutritionRepository.save(dailyNutrition);


            week.put(i, dailyNutrition);
        }


        Set<DailyNutrition> weeklyNutrition = new HashSet<>();
        for(int i = 0; i < 7; i++){
            DailyNutrition day = (DailyNutrition) week.get(i);
            weeklyNutrition.add(day);
        }

        return weeklyNutrition;
    }

    private Set<Food> checkForRepetition(Dictionary week, int today, List<Food> todaysChoice) {

        Set<Food> finalChoice = new HashSet<>();
        Set<Food> allFoodTillToday = new HashSet<>();
        for(int i = 0; i < today; i++){
            DailyNutrition day = (DailyNutrition) week.get(i);
            allFoodTillToday.addAll(day.getDailyFood());
        }
        for (Food food : todaysChoice) {
            if (allFoodTillToday.contains(food)
                    || allFoodTillToday.contains(food)) {
                // replace
                finalChoice.add(replaceFood(allFoodTillToday, food));
            } else {
                finalChoice.add(food);
            }
        }

        return finalChoice;
    }

    private Food replaceFood(Set<Food> allFoodTillToday, Food food) {
        for (Food foundFood : foodRepository.findAllByFoodGroup(food.getFoodGroup())) {
            if (foundFood.getDescription() != food.getDescription()
                    && foundFood.getEnergy_kcal() > food.getEnergy_kcal() - 50 &&
                    foundFood.getEnergy_kcal() < food.getEnergy_kcal() + 50) {
                if(!allFoodTillToday.contains(foundFood)) {
                    return foundFood;
                }
            }
        }
        return new Food();
    }

    private Food findSingleFood(double calorieLimit, int fatLevel, Set<Food> foodList, List<String> forbiddenFood) {
        Food foundFood = new Food();
        for (Food food : foodList) {
            if (!forbiddenFood.contains(food.getDescription())) {
                if (food.getEnergy_kcal() <= calorieLimit) {
                    if (fatLevel > 0) {
                        if (food.getFats() > 50) {
                            foundFood = food;
                            break;
                        }
                    } else {
                        foundFood = food;
                        break;
                    }
                }
            }
        }
        return foundFood;
    }


}
