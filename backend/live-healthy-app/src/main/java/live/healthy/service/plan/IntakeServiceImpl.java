package live.healthy.service.plan;

import live.healthy.events.IntakeSubmitEvent;
import live.healthy.events.IntakeSubmitType;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.NutritionPlanDto;
import live.healthy.facts.model.plan.DailyNutrition;
import live.healthy.facts.model.plan.NutritionPlan;
import live.healthy.facts.model.plan.PlanFollowingEnum;
import live.healthy.facts.model.user.User;
import live.healthy.repository.nutrition.DailyNutritionRepository;
import live.healthy.repository.plan.NutritionPlanRepository;
import live.healthy.repository.user.UserRepository;
import live.healthy.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntakeServiceImpl implements IntakeService {


    private final KieContainer kieContainer;
    private final UserRepository userRepository;
    private final DailyNutritionRepository dailyNutritionRepository;
    private final NutritionPlanRepository nutritionPlanRepository;

    @Override
    public NutritionPlanDto submit(Long userId, int dayIndex, double caloriesDifference) throws UserNotFound {

        KieSession kieSession = kieContainer.newKieSession("intakeSubmitSession");

        boolean regular = false;
        if (caloriesDifference == 0) {
            // input is regular
            regular = true;
        }


        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        NutritionPlan nutritionPlan = user.getNutritionPlan();

        // Remembering new submit in database
        for (DailyNutrition dailyNutrition: nutritionPlan.getWeeklyPlan()) {
            if (dailyNutrition.getDayOfTheWeek() == dayIndex) {
                IntakeSubmitType intakeSubmitType = IntakeSubmitType.REGULAR;
                if(!regular) {
                    intakeSubmitType = IntakeSubmitType.IRREGULAR;
                }
                dailyNutrition.setIntakeSubmitType(intakeSubmitType);
                dailyNutrition = dailyNutritionRepository.save(dailyNutrition);
                break;
            }
        }
        nutritionPlan = nutritionPlanRepository.save(nutritionPlan);

        kieSession.insert(nutritionPlan);
        kieSession.insert(user);

        // ispaljujem dogadjaje za svaki dan submita, moze biti reguar ili iregular, fora je sto pravila treba da skontaju
        // kolko ih ima i kakvi su i da reaguje
        for (DailyNutrition dailyNutrition: nutritionPlan.getWeeklyPlan()) {
            kieSession.insert(new IntakeSubmitEvent(user.getId(), dailyNutrition.getIntakeSubmitType()));
        }


        long ruleFireCount = kieSession.fireAllRules();
        System.out.println(ruleFireCount + " rules fired");

        // If user deserved reward and there is still days in this week
        if(nutritionPlan.getPlanFollowingEnum() == PlanFollowingEnum.REWARDED && dayIndex != 6) {
            for(DailyNutrition dailyNutrition: nutritionPlan.getWeeklyPlan()) {
                if(dailyNutrition.getDayOfTheWeek() == dayIndex+1) {
                    // user is rewarded with not needing to follow tomorrows plan
                    dailyNutrition.setIntakeSubmitType(IntakeSubmitType.REWARDED_NOT_NEEDED);
                } else if (dailyNutrition.getDayOfTheWeek() > dayIndex) {
                    dailyNutrition.setIntakeSubmitType(IntakeSubmitType.NONE);
                }
                else {
                    dailyNutrition.setIntakeSubmitType(IntakeSubmitType.REGISTERED_NOT_NEEDED);
                }
                dailyNutritionRepository.save(dailyNutrition);
            }
        }

        // If user deserved punishment and there is still days in this week
        if(nutritionPlan.getPlanFollowingEnum() == PlanFollowingEnum.PUNISHED && dayIndex != 6) {
            int daysRemaining = 6 - dayIndex;
            double caloriesDifferenceByDay = caloriesDifference / daysRemaining;
            nutritionPlan.setCaloriesGoal(nutritionPlan.getCaloriesGoal() + caloriesDifferenceByDay);
//            for(DailyNutrition dailyNutrition: nutritionPlan.getWeeklyPlan()) {
//                if(dailyNutrition.getDayOfTheWeek() > dayIndex){
//
//                }
//            }
        }


        System.out.println(nutritionPlan.getPlanFollowingEnum());
        nutritionPlan = nutritionPlanRepository.save(nutritionPlan);

        return ObjectMapperUtils.map(nutritionPlan, NutritionPlanDto.class);
    }
}
