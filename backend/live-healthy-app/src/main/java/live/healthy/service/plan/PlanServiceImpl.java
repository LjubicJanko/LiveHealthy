package live.healthy.service.plan;

import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.CoefficientsDto;
import live.healthy.facts.model.user.User;
import live.healthy.repository.UserRepository;
import live.healthy.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.builder.KieBuilder;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private static final double MALE_IBW_COEFFICIENT = 1.41;
    private static final double FEMALE_IBW_COEFFICIENT = 1.36;
    private static final double FEET_COEFFICIENT = 30.48;
    private static final double BFP_BMI_COEFFICIENT = 1.20;
    private static final double BFP_AGE_COEFFICIENT = 0.23;
    private static final double BFP_MALE_COEFFICIENT = 16.2;
    private static final double BFP_FEMALE_COEFFICIENT = 5.4;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private final UserRepository userRepository;
    private final KieContainer kieContainer;

    @Override
    public void createPlan(Long userId) throws UserNotFound {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        double bmi = bodyMassIndex(user.getHeight(), user.getWeight());
        double ibw = idealBodyWeight(user.getHeight(), user.getWeight(), user.isSex());
        double bfp = bodyFatPercentage(bmi, user.getAge(), user.isSex());
        String bodyType = user.getBodyType().getType();
        CoefficientsDto coefficientsDto = new CoefficientsDto(bmi, ibw);

        KieBase kieBase = kieContainer.getKieBase();
//        KieSession kieSession = kieContainer.newKieSession();
        KieSession kieSession = kieBase.newKieSession();

        kieSession.setGlobal("age", user.getAge());
        kieSession.setGlobal("weight", user.getWeight());
        kieSession.setGlobal("idealBodyWeight", ibw);
        kieSession.setGlobal("bodyMassIdx", bmi);
        kieSession.setGlobal("bodyFatPercentage", bfp);
        kieSession.setGlobal("bodyType", bodyType);
        kieSession.setGlobal("sex", user.isSex());

        kieSession.insert(user);
        kieSession.insert(coefficientsDto);


        kieSession.fireAllRules();
        kieSession.dispose();

        for (Object obj : kieSession.getObjects()) {
            System.out.println(obj.getClass().getName());
        }

    }


    /**
     * How is it calculated? Square your height in meters. Divide your weight in kilograms by the number you obtained.
     * For example: your height is 170cm; your weight is 65kg. Therefore, 65 : (1.7 * 1.7) = 22.5
     *
     * @param height -	body height
     * @param weight -	body weight
     * @return
     */
    private double bodyMassIndex(double height, double weight) {
        double heightInMetersSquared = Math.pow(height / 100, 2);
        return Double.parseDouble(df2.format(weight / heightInMetersSquared));
    }

    /**
     * Calculates ideal body weight by D. R. Miller formula from 1983.
     * Calculating is different for male and female
     *
     * @param height
     * @param weight
     * @return
     */
    private double idealBodyWeight(double height, double weight, boolean sex) {
        double baseWeight;
        double heightInInchesOverFiveFeet = 0;
        double ibw;
        double heightInFeet = height / FEET_COEFFICIENT;


        if (heightInFeet > 5) {
            heightInInchesOverFiveFeet = 12 * (heightInFeet - 5);
        }
        if (sex) {
            baseWeight = 56.2;
            ibw = baseWeight + heightInInchesOverFiveFeet * MALE_IBW_COEFFICIENT;
        } else {
            baseWeight = 53.1;
            ibw = baseWeight + heightInInchesOverFiveFeet * FEMALE_IBW_COEFFICIENT;
        }
        return Double.parseDouble(df2.format(ibw));
    }

    private double bodyFatPercentage(double bmi, double age, boolean sex) {
        double bfp = 0;
        if (sex) {
            bfp = BFP_BMI_COEFFICIENT * bmi + BFP_AGE_COEFFICIENT * age - BFP_MALE_COEFFICIENT;
        } else {
            bfp = BFP_BMI_COEFFICIENT * bmi + BFP_AGE_COEFFICIENT * age - BFP_FEMALE_COEFFICIENT;
        }
        return Double.parseDouble(df2.format(bfp));
    }
}
