package live.healthy.service;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import org.kie.api.runtime.Globals;
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
    private static DecimalFormat df2 = new DecimalFormat("#.##");

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

    /**
     * How is it calculated? Square your height in meters. Divide your weight in kilograms by the number you obtained.
	 * For example: your height is 170cm; your weight is 65kg. Therefore, 65 : (1.7 * 1.7) = 22.5
     *
     * @param height	-	body height
     * @param weight	-	body weight
     * @return
     */
    private double bodyMassIndex(double height, double weight) {
		double heightInMetersSquared = Math.pow(height / 100, 2);
		return Double.parseDouble(df2.format(weight / heightInMetersSquared));
    }

}
