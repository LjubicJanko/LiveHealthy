package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.CoefficientsDto;
import live.healthy.facts.dto.PlanDto;
import live.healthy.facts.model.Type;
import live.healthy.facts.model.food.Food;
import live.healthy.facts.model.plan.Goal;
import live.healthy.facts.model.plan.NutritionPlan;
import live.healthy.facts.model.plan.TrainingPlan;
import live.healthy.facts.model.user.User;
import live.healthy.repository.UserRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.text.DecimalFormat;

import static live.healthy.common.util.BodyIndexUtil.*;

@Service
public class PlanServiceImpl implements PlanService {
    private static Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);


    private UserRepository userRepository;
    private KieContainer kieContainer;

    @Autowired
    public PlanServiceImpl(KieContainer kieContainer, UserRepository userRepository) {
        log.info("Initialising a new creatingPlan session.");
        this.kieContainer = kieContainer;
        this.userRepository = userRepository;
    }


    @Override
    public void createPlan(Long userId, List<String> forbiddenFood ) throws UserNotFound {
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
        NutritionPlan nutritionPlan = new NutritionPlan();
        nutritionPlan.setGoal(Goal.valueOf(planDto.getGoal()));

        KieSession kieSession = kieContainer.newKieSession("creatingNutritionPlan");

        kieSession.insert(user);
        kieSession.insert(planDto);

        int i = kieSession.fireAllRules();
        System.out.println(planDto);
        kieSession.dispose();



    }


}
