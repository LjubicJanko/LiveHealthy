package live.healthy.service.plan;

import live.healthy.events.IntakeSubmitEvent;
import live.healthy.events.IntakeSubmitType;
import live.healthy.exception.plan.NutritionPlanAlreadyExists;
import live.healthy.exception.plan.NutritionPlanNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.NutritionPlanDto;
import live.healthy.facts.dto.NutritionWrapperDto;
import live.healthy.facts.dto.PlanDto;
import live.healthy.facts.model.food.Food;
import live.healthy.facts.model.plan.DailyNutrition;
import live.healthy.facts.model.plan.Goal;
import live.healthy.facts.model.plan.NutritionPlan;
import live.healthy.facts.model.plan.PlanFollowingEnum;
import live.healthy.facts.model.user.User;
import live.healthy.repository.nutrition.DailyNutritionRepository;
import live.healthy.repository.nutrition.FoodRepository;
import live.healthy.repository.plan.NutritionPlanRepository;
import live.healthy.repository.user.UserRepository;
import live.healthy.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private static Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);
    private static DecimalFormat df2 = new DecimalFormat("#.##");


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
    public NutritionPlanDto getNutritionPlan(Long id) throws NutritionPlanNotFound {
        NutritionPlan nutritionPlan = nutritionPlanRepository.findById(id).orElseThrow(() -> new NutritionPlanNotFound(id));
        return ObjectMapperUtils.map(nutritionPlan, NutritionPlanDto.class);
    }

    @Override
    public NutritionPlanDto createPlan(Long userId) throws UserNotFound, NutritionPlanAlreadyExists {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        /*Todo: change this condition when new plan is being created after one week*/
        if (user.getNutritionPlan() != null) {
            throw new NutritionPlanAlreadyExists(userId);
        }

        KieSession kieSession = kieContainer.newKieSession("creatingPlan");

        kieSession.setGlobal("bodyType", user.getBodyType().getBodyTypeEnum());
        PlanDto planDto = new PlanDto("", 0, 0.0);

        kieSession.insert(planDto);
        kieSession.insert(user);


        int i = kieSession.fireAllRules();
        kieSession.dispose();
        NutritionPlanDto nutritionPlanDto = createWeeklyNutrition(planDto, user);

        return nutritionPlanDto;

    }

    /**
     * Method fires rules for creating nutrition plan and calls appropriate methods for arranging meals for 7 days in the
     * week, based on information that rules provided.
     *
     * @param planDto
     * @param user
     * @return
     */
    private NutritionPlanDto createWeeklyNutrition(PlanDto planDto, User user) {
        KieSession kieSession = kieContainer.newKieSession("creatingNutritionPlan");

        kieSession.insert(user);
        kieSession.insert(planDto);

        kieSession.fireAllRules();
        kieSession.dispose();


        double calorieLimit = Double.parseDouble(df2.format(user.getStartingBmr() * planDto.getBmrPercentNeeded()));

        NutritionPlan nutritionPlan = new NutritionPlan();
        nutritionPlan.setGoal(Goal.valueOf(planDto.getGoal()));
        nutritionPlan.setWeeklyPlan(findWeeklyFoodPlan(nutritionPlan.getGoal(), calorieLimit,
                planDto.getFatLevelNeeded(), user.getId()));
        nutritionPlan.setCaloriesGoal(calorieLimit);
        nutritionPlan.setFatBased(planDto.fatLevelNeeded);
        nutritionPlan.setPlanFollowingEnum(PlanFollowingEnum.REGULAR);

        user.setNutritionPlan(nutritionPlanRepository.save(nutritionPlan));
        userRepository.save(user);

        return ObjectMapperUtils.map(nutritionPlan, NutritionPlanDto.class);
    }

    /**
     * Mid-method that collects food for several food groups and calls method for creating week plan
     *
     * @param goal
     * @param calorieLimit
     * @param fatLevel
     * @return
     */
    private Set<DailyNutrition> findWeeklyFoodPlan(Goal goal, double calorieLimit, int fatLevel, Long userId) {
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

        Set<DailyNutrition> weeklyNutrition = getWeeklyNutrition(goal, nutritionWrapperDto, fatLevel, calorieLimit, userId);

        return weeklyNutrition;
    }

    /**
     * Provides collection of meals for every day in the week
     *
     * @param goal
     * @param nutritionWrapperDto
     * @param fatLevel
     * @param calorieLimit
     * @return
     */
    private Set<DailyNutrition> getWeeklyNutrition(Goal goal, NutritionWrapperDto nutritionWrapperDto,
                                                   int fatLevel, double calorieLimit, Long userId) {
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
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getCereals());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getFruits());
                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case MILD_WEIGHT_LOSS:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getFruits());
                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case EXTREME_WEIGHT_LOSS:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    choosenFood = Arrays.asList(breakfast, lunch);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case MAINTAIN_WEIGHT:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta());
                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case WEIGHT_GAIN:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getEggs());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta());
                    snackCalories = calorieLimit - breakfast.getEnergy_kcal() -
                            lunch.getEnergy_kcal() - dinner.getEnergy_kcal();
                    snack = findSingleFood(snackCalories, fatLevel, nutritionWrapperDto.getSnacks());

                    choosenFood = Arrays.asList(breakfast, lunch, dinner);
                    dailySnacks.add(snack);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case MILD_WEIGHT_GAIN:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getCereals());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta());
                    snackCalories = calorieLimit - breakfast.getEnergy_kcal() -
                            lunch.getEnergy_kcal() - dinner.getEnergy_kcal();
                    snack = findSingleFood(snackCalories, fatLevel, nutritionWrapperDto.getSnacks());
                    dailySnacks.add(snack);

                    choosenFood = Arrays.asList(breakfast, lunch, dinner);

                    if (i > 0) {
                        dailyFood.addAll(checkForRepetition(week, i, choosenFood));
                    } else {
                        dailyFood.addAll(choosenFood);
                    }
                    break;

                case EXTREME_WEIGHT_GAIN:
                    breakfast = findSingleFood(breakfastCalories, fatLevel, nutritionWrapperDto.getCereals());
                    lunch = findSingleFood(lunchCalories, fatLevel, nutritionWrapperDto.getChicken());
                    dinner = findSingleFood(dinnerCalories, fatLevel, nutritionWrapperDto.getPasta());
                    snackCalories = calorieLimit - breakfast.getEnergy_kcal() -
                            lunch.getEnergy_kcal() - dinner.getEnergy_kcal();
                    snack = findSingleFood(snackCalories / 2, fatLevel, nutritionWrapperDto.getSnacks());
                    snacks_fruit = findSingleFood(snackCalories / 2, fatLevel, nutritionWrapperDto.getFruits());
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
            dailyNutrition.setIntakeSubmitType(IntakeSubmitType.NONE);
            dailyNutrition.setDayOfTheWeek(i);

            dailyNutritionRepository.save(dailyNutrition);


            week.put(i, dailyNutrition);
        }


        Set<DailyNutrition> weeklyNutrition = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            DailyNutrition day = (DailyNutrition) week.get(i);
            weeklyNutrition.add(day);
        }

        return weeklyNutrition;
    }

    /**
     * Method checks (and replaces) if some meal from today choice is already chosen since start of the week.
     *
     * @param week  -   collection of nutrition since start of the week
     * @param today -   number in the week that today is
     * @param todaysChoice  -   collection of food picked for today
     *
     * @return  Set<Food> that will be used as today choice
     */
    private Set<Food> checkForRepetition(Dictionary week, int today, List<Food> todaysChoice) {

        Set<Food> finalChoice = new HashSet<>();
        Set<Food> allFoodTillToday = new HashSet<>();
        for (int i = 0; i < today; i++) {
            DailyNutrition day = (DailyNutrition) week.get(i);
            allFoodTillToday.addAll(day.getDailyFood());
        }
        for (Food food : todaysChoice) {
            if (allFoodTillToday.contains(food)) {
                finalChoice.add(replaceFood(allFoodTillToday, food)); // replace
            } else {
                finalChoice.add(food);
            }
        }

        return finalChoice;
    }

    /**
     * Method used to find replacement for food that is already picked for some day of the week,
     * in order not to pick it twice
     *
     * @param allFoodTillToday - set of all food picked for meals for days since week start
     * @param food             -   food to be replaced
     * @return -   Food object
     */
    private Food replaceFood(Set<Food> allFoodTillToday, Food food) {
        for (Food foundFood : foodRepository.findAllByFoodGroup(food.getFoodGroup())) {
            if (!foundFood.getDescription().equals(food.getDescription()) &&
                    foundFood.getEnergy_kcal() > food.getEnergy_kcal() - 50 &&
                    foundFood.getEnergy_kcal() < food.getEnergy_kcal() + 50 &&
                    !allFoodTillToday.contains(foundFood)) {
                return foundFood;
            }
        }
        return food;    // no good replacement can be found
    }

    /**
     * Method used to find appropriate food from given food list but with condition of not exceeding calorieLimit
     * and having appropriate amount of fat, determinated by fatLevel property
     *
     * @param calorieLimit -   limit of kcal that found food can have
     * @param fatLevel     -   0 (normal level of fat), -1 (absolutely non fat), 1 (fat over 50)
     * @param foodList     -   list of food from same food group from which we extract single food
     * @return Food object
     */
    private Food findSingleFood(double calorieLimit, int fatLevel, Set<Food> foodList) {
        Food foundFood = new Food();
        for (Food food : foodList) {
            if (food.getEnergy_kcal() <= calorieLimit) {
                if (fatLevel > 0) {
                    if (food.getFats() > 50) {
                        foundFood = food;
                        break;
                    }
                } else if (fatLevel == 0) {
                    if (food.getFats() < 3) {
                        foundFood = food;
                        break;
                    }
                } else {
                    foundFood = food;
                    break;
                }
            }
        }
        return foundFood;
    }


}
