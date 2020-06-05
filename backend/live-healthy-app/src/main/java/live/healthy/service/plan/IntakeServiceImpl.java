package live.healthy.service.plan;

import live.healthy.events.IntakeSubmitEvent;
import live.healthy.events.IntakeSubmitType;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.model.plan.DailyNutrition;
import live.healthy.facts.model.plan.NutritionPlan;
import live.healthy.facts.model.plan.PlanFollowingEnum;
import live.healthy.facts.model.user.User;
import live.healthy.repository.nutrition.DailyNutritionRepository;
import live.healthy.repository.plan.NutritionPlanRepository;
import live.healthy.repository.user.UserRepository;
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
    public void submit(Long userId, int dayIndex, double caloriesDifference) throws UserNotFound {

        KieSession kieSession = kieContainer.newKieSession("intakeSubmitSession");

        boolean regular = false;
        if (caloriesDifference == 0) {
            // input is regular
            regular = true;
        }


        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        NutritionPlan nutritionPlan = user.getNutritionPlan();

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
        nutritionPlan = nutritionPlanRepository.save(nutritionPlan);

        // If user deserved reward and there is still days in this week
        if(nutritionPlan.getPlanFollowingEnum() == PlanFollowingEnum.REWARDED && dayIndex != 6) {
            for(DailyNutrition dailyNutrition: nutritionPlan.getWeeklyPlan()) {
                if(dailyNutrition.getDayOfTheWeek() == dayIndex+1) {
                    // user is rewarded with not needing to follow tomorrows plan
                    dailyNutrition.setIntakeSubmitType(IntakeSubmitType.REWARDED_NOT_NEEDED);
                } else {
                    dailyNutrition.setIntakeSubmitType(IntakeSubmitType.NONE);
                }
                dailyNutritionRepository.save(dailyNutrition);
            }
        }
        System.out.println(nutritionPlan.getPlanFollowingEnum());

    }
}
