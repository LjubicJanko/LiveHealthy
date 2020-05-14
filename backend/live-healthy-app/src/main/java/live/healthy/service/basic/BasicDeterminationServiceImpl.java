package live.healthy.service.basic;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class BasicDeterminationServiceImpl implements BasicDeterminationService {
    private static Logger log = LoggerFactory.getLogger(BasicDeterminationService.class);

    private final KieContainer kieContainer;

    @Autowired
    public BasicDeterminationServiceImpl(KieContainer kieContainer) {
        log.info("Initialising a new example session.");
        this.kieContainer = kieContainer;
    }

    @Override
    public BodyTypeDto determine(BodyInfoDto bodyInfoDto) {
        BodyTypeDto bodyTypeDto = new BodyTypeDto();
        bodyTypeDto.setType("");
        KieSession kieSession = kieContainer.newKieSession();

        kieSession.insert(bodyInfoDto);
        kieSession.insert(bodyTypeDto);
        kieSession.fireAllRules();
        kieSession.dispose();
        return bodyTypeDto;
    }


}
