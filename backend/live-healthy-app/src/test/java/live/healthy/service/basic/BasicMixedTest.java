package live.healthy.service.basic;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertEquals;

public class BasicMixedTest {

    @Test
    public void testMesoEctoBody() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("basicDetermination");

        BodyInfoDto bodyInfoDto = new BodyInfoDto("Narrower than my hips", "Average",
                "Stay skinny", "Long and narrow",
                "I can gain and lose without too much of a struggle", true);

        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("ECTOMORPH_MESOMORPH", bodyTypeDto.getType());
        assertEquals("YOUR BODY IS BETWEEN AN ECTOMORPH AND A MESOMORPH.",
                bodyTypeDto.getDescription());
    }


    @Test
    public void testEndoMesoBody() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("basicDetermination");

        BodyInfoDto bodyInfoDto = new BodyInfoDto("Wider than my hips", "Big",
                "Carry a bit of extra fat", "Round and soft",
                "I can gain and lose without too much of a struggle", true);

        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("ENDOMORPH_MESOMORPH", bodyTypeDto.getType());
        assertEquals("YOUR BODY IS BETWEEN AN MESOMORPH AND A ENDOMORPH.",
                bodyTypeDto.getDescription());
    }


    @Test
    public void testNonexistentBodyType() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("live.healthy.kjar", "live-healthy-kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kContainer.newKieSession("basicDetermination");

        BodyInfoDto bodyInfoDto = new BodyInfoDto("Narrower than my hips", "Big",
                "Stay skinny", "Long and narrow",
                "I can gain and lose without too much of a struggle", true);

        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("NONEXISTENT", bodyTypeDto.getType());
        assertEquals("Information about your body is contradictory. We cannot determine your type.",
                bodyTypeDto.getDescription());
    }
}
