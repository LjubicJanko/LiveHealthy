package live.healthy.service.basic;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import live.healthy.facts.model.body_desc_enums.*;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BasicTest {


    private static Integer numInEcto;
    private static Integer numInMeso;
    private static Integer numInEndo;

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

        findDescriptionGroupAppearances(bodyInfoDto);

        kieSession.setGlobal("ecto_meso", numInEcto * numInMeso);
        kieSession.setGlobal("meso_endo", numInEndo * numInMeso);
        kieSession.setGlobal("ecto", Math.abs(numInEcto - numInMeso));
        kieSession.setGlobal("meso", Math.abs(numInEndo - numInEcto - numInMeso));
        kieSession.setGlobal("endo", Math.abs(numInEndo - numInMeso));

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


        findDescriptionGroupAppearances(bodyInfoDto);

        kieSession.setGlobal("ecto_meso", numInEcto * numInMeso);
        kieSession.setGlobal("meso_endo", numInEndo * numInMeso);
        kieSession.setGlobal("ecto", Math.abs(numInEcto - numInMeso));
        kieSession.setGlobal("meso", Math.abs(numInEndo - numInEcto - numInMeso));
        kieSession.setGlobal("endo", Math.abs(numInEndo - numInMeso));

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


        findDescriptionGroupAppearances(bodyInfoDto);

        kieSession.setGlobal("ecto_meso", numInEcto * numInMeso);
        kieSession.setGlobal("meso_endo", numInEndo * numInMeso);
        kieSession.setGlobal("ecto", Math.abs(numInEcto - numInMeso));
        kieSession.setGlobal("meso", Math.abs(numInEndo - numInEcto - numInMeso));
        kieSession.setGlobal("endo", Math.abs(numInEndo - numInMeso));

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);

        int numOfRulesFired = kieSession.fireAllRules();

        assertEquals(1, numOfRulesFired);
        assertEquals("mesomorph", bodyTypeDto.getType());
        assertEquals("You have the body type that finds it easiest to add new muscle and you don’t tend to store much body fat.",
                bodyTypeDto.getDescription());
    }

    private void findDescriptionGroupAppearances(BodyInfoDto bodyInfoDto) {

        Map<String, String> bodyTypeDescription = new HashMap<>() {{
            put("Shoulders", bodyInfoDto.getShoulders());
            put("Forearms", bodyInfoDto.getForearms());
            put("BodyLook", bodyInfoDto.getBodyLook());
            put("WeightTendations", bodyInfoDto.getWeightTendations());
            put("BodyTendations", bodyInfoDto.getBodyTendations());
        }};

        Map<String, String> ectoGroup = new HashMap<>() {{
            put("Shoulders", Shoulders.NARROWER_THAN_HIPS.getValue());
            put("Forearms", Forearms.SMALL.getValue());
            put("BodyLook", BodyLook.LONG_AND_NARROW.getValue());
            put("WeightTendations", WeightTendations.CAN_NOT_GAIN.getValue());
            put("BodyTendations", BodyTendations.SKINNY.getValue());
        }};

        Map<String, String> mesoGroup = new HashMap<>() {{
            put("Shoulders", Shoulders.SAME_AS_HIPS.getValue());
            put("Forearms", Forearms.AVERAGE.getValue());
            put("BodyLook", BodyLook.SQUARE_AND_RUGGED.getValue());
            put("WeightTendations", WeightTendations.CAN_GAIN_AND_LOSE.getValue());
            put("BodyTendations", BodyTendations.LEAN_YET_MUSCULAR.getValue());
        }};

        Map<String, String> endoGroup = new HashMap<>() {{
            put("Shoulders", Shoulders.WIDER_THAN_HIPS.getValue());
            put("Forearms", Forearms.BIG.getValue());
            put("BodyLook", BodyLook.ROUND_AND_SOFT.getValue());
            put("WeightTendations", WeightTendations.CAN_GAIN_CAN_NOT_LOSE.getValue());
            put("BodyTendations", BodyTendations.CARRY_EXTRA_FAT.getValue());
        }};

        numInEcto = 0;
        numInMeso = 0;
        numInEndo = 0;

        for (String descriptionKey : bodyTypeDescription.keySet()) {
            if (ectoGroup.get(descriptionKey).equals(bodyTypeDescription.get(descriptionKey))) {
                numInEcto += 1;
            } else if (mesoGroup.get(descriptionKey).equals(bodyTypeDescription.get(descriptionKey))) {
                numInMeso += 1;
            } else if (endoGroup.get(descriptionKey).equals(bodyTypeDescription.get(descriptionKey))) {
                numInEndo += 1;
            }
        }
        if (numInEcto * numInMeso * numInEndo != 0) {
            // nonexistent type for sure.
            numInEcto = 0;
            numInMeso = 0;
            numInEndo = 0;
        }
    }
}
