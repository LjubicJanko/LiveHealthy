package live.healthy.service.nutritionPlan;

import live.healthy.facts.dto.PlanDto;
import live.healthy.facts.model.BodyType;
import live.healthy.facts.model.BodyTypeEnum;
import live.healthy.facts.model.user.User;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertEquals;

public class CreatePlanTest {

    @Test
    public void maintainWeightTest() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("creatingPlan");


        User user = new User();
        user.setWeight(85.0);
        user.setIdealBodyWeight(87.0);

        PlanDto planDto = new PlanDto();

        kieSession.insert(user);
        kieSession.insert(planDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("MAINTAIN_WEIGHT", planDto.getGoal());
    }

    @Test
    public void extreemeWeightGainNoFatTest() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("creatingPlan");

        User user = new User();
        user.setWeight(50.0);
        user.setIdealBodyWeight(87.0);
        user.setStartingBmi(20.0);
        user.setSex(true);
        user.setAge(18);
        user.setStartingBfp(9.0);

        BodyType bodyType = new BodyType();
        bodyType.setBodyTypeEnum(BodyTypeEnum.ECTOMORPH);

        PlanDto planDto = new PlanDto();
        planDto.setGoal("");

        kieSession.setGlobal("bodyType", BodyTypeEnum.ECTOMORPH.name());

        kieSession.insert(user);
        kieSession.insert(planDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(2, numOfRulesFired);
        assertEquals("EXTREME_WEIGHT_GAIN", planDto.getGoal());
    }


    @Test
    public void extreemeWeightGainWithMoreFatsTest() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("creatingPlan");

        User user = new User();
        user.setWeight(50.0);
        user.setIdealBodyWeight(87.0);
        user.setStartingBmi(20.0);
        user.setSex(false);
        user.setAge(18);
        user.setStartingBfp(7.5);

        BodyType bodyType = new BodyType();
        bodyType.setBodyTypeEnum(BodyTypeEnum.ECTOMORPH);

        PlanDto planDto = new PlanDto();
        planDto.setGoal("");

        kieSession.setGlobal("bodyType", BodyTypeEnum.ECTOMORPH.name());

        kieSession.insert(user);
        kieSession.insert(planDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(3, numOfRulesFired);
        assertEquals("EXTREME_WEIGHT_GAIN", planDto.getGoal());
    }

}
