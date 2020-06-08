package live.healthy.service.basic;

import live.healthy.exception.user.BodyTypeNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import live.healthy.facts.model.BodyType;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class BasicDeterminationServiceImpl implements BasicDeterminationService {
    private static Logger log = LoggerFactory.getLogger(BasicDeterminationService.class);

    private final KieContainer kieContainer;
    private final BodyTypeRepository bodyTypeRepository;
    private final UserRepository userRepository;
    private final BodyDescriptionRepository bodyDescriptionRepository;

//    @Autowired
//    public BasicDeterminationServiceImpl(KieContainer kieContainer) {
//        log.info("Initialising a new basicDetermination session.");
//        this.kieContainer = kieContainer;
//    }

    @Override
    public BodyTypeDto determine(BodyInfoDto bodyInfoDto, Long userId) throws BodyTypeNotFound, UserNotFound {
        BodyTypeDto bodyTypeDto = new BodyTypeDto("", "");


//        KieSession kieSession = kieContainer.newKieSession();
        KieSession kieSession = kieContainer.newKieSession("basicDetermination");

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


}
