package live.healthy.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;

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

		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(bodyInfoDto);
		kieSession.insert(bodyTypeDto);
		kieSession.fireAllRules();
		kieSession.dispose();
		for (Object obj : kieSession.getObjects()) {
			System.out.println(obj.getClass());
			System.out.println(obj.toString());
		}
		return bodyTypeDto;
	}

}
