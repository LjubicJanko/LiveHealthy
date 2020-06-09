package live.healthy.service.basic;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertEquals;

public class BasicTest {

    @Test
    public void testEctomorphBody() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("basicDetermination");

        BodyInfoDto bodyInfoDto = new BodyInfoDto("Narrower than my hips", "Small",
                "Stay skinny", "Long and narrow",
                "Have trouble gaining weight in the form of muscle or fat", true);

        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("ectomorph", bodyTypeDto.getType());
        assertEquals("Ectomorphs are good at processing carbohydrates into energy and your fast metabolism means that you burn off fat easily.",
                bodyTypeDto.getDescription());
    }

    @Test
    public void testEndomorphBody() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("basicDetermination");

        BodyInfoDto bodyInfoDto = new BodyInfoDto("Wider than my hips", "Big",
                "Carry a bit of extra fat", "Round and soft",
                "Gain weight easily, but find it hard to lose", true);

        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("endomorph", bodyTypeDto.getType());
        assertEquals("Endomorphs are adept at storing fuel, with muscle and fat concentrated in the lower body",
                bodyTypeDto.getDescription());
    }


    @Test
    public void testMesomorphBody() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("basicDetermination");

        BodyInfoDto bodyInfoDto = new BodyInfoDto("The same width as my hips", "Average",
                "Stay lean, yet muscular", "Square and rugged",
                "I can gain and lose without too much of a struggle", true);

        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("mesomorph", bodyTypeDto.getType());
        assertEquals("You have the body type that finds it easiest to add new muscle and you don’t tend to store much body fat.",
                bodyTypeDto.getDescription());
    }
}
