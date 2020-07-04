package live.healthy.service.basic;

import live.healthy.exception.user.BodyTypeNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import live.healthy.facts.model.BodyType;
import live.healthy.facts.model.body_desc_enums.*;
import live.healthy.facts.model.user.BodyDescription;
import live.healthy.facts.model.user.User;
import live.healthy.repository.user.BodyDescriptionRepository;
import live.healthy.repository.user.BodyTypeRepository;
import live.healthy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasicDeterminationServiceImpl implements BasicDeterminationService {
    private static Logger log = LoggerFactory.getLogger(BasicDeterminationService.class);

    private final KieContainer kieContainer;
    private final BodyTypeRepository bodyTypeRepository;
    private final UserRepository userRepository;
    private final BodyDescriptionRepository bodyDescriptionRepository;

    private static Integer numInEcto;
    private static Integer numInMeso;
    private static Integer numInEndo;


    @Override
    public BodyTypeDto determine(BodyInfoDto bodyInfoDto, Long userId) throws BodyTypeNotFound, UserNotFound {
        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");
        KieSession kieSession = kieContainer.newKieSession("basicDetermination");

        findDescriptionGroupAppearances(bodyInfoDto);

        kieSession.setGlobal("ecto_meso", numInEcto * numInMeso);
        kieSession.setGlobal("meso_endo", numInEndo * numInMeso);
        kieSession.setGlobal("ecto", Math.abs(numInEcto - numInMeso));
        kieSession.setGlobal("meso", Math.abs(numInEndo - numInEcto - numInMeso));
        kieSession.setGlobal("endo", Math.abs(numInEndo - numInMeso));

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);
        kieSession.fireAllRules();
        kieSession.dispose();

        BodyType bodyType = bodyTypeRepository.findByDescription(bodyTypeDto.getDescription())
                .orElseThrow(() -> new BodyTypeNotFound());
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        user.setBodyType(bodyType);

        BodyDescription bodyDescription = new BodyDescription();

        bodyDescription.setShoulders(bodyInfoDto.getShoulders());
        bodyDescription.setForearms(bodyInfoDto.getForearms());
        bodyDescription.setBodyTendations(bodyInfoDto.getBodyTendations());
        bodyDescription.setBodyLook(bodyInfoDto.getBodyLook());
        bodyDescription.setWeightTendations(bodyInfoDto.getWeightTendations());

        user.setBodyDescription(bodyDescriptionRepository.save(bodyDescription));

        userRepository.save(user);


        return bodyTypeDto;
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
