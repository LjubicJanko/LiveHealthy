package live.healthy.service.dynamic;

import live.healthy.events.dynamic.ChangeUserLimitEvent;
import live.healthy.events.dynamic.Event;
import live.healthy.facts.dto.CreateRuleDto;
import live.healthy.facts.model.dynamic.AlertDecision;
import live.healthy.facts.model.dynamic.Condition;
import live.healthy.facts.model.dynamic.Rule;
import lombok.RequiredArgsConstructor;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DynamicServiceImpl implements  DynamicService {

    private final KieContainer kieContainer;

    @Override
    public void createRule(CreateRuleDto createRuleDto) throws Exception {
        ChangeUserLimitEvent changeUserLimitEvent = new ChangeUserLimitEvent();
        changeUserLimitEvent.setAge(55);

        Rule ageLimitRule = new Rule();

        Condition ageOverLimit = new Condition();
        ageOverLimit.setField("age");
        ageOverLimit.setOperator(Condition.Operator.GREATER_THAN);
        ageOverLimit.setValue(createRuleDto.getAgeLowLimit());

        Condition ageUnderLimit = new Condition();
        ageUnderLimit.setField("age");
        ageUnderLimit.setOperator(Condition.Operator.LESS_THAN);
        ageUnderLimit.setValue(createRuleDto.getAgeHighLimit());

        // In reality, you would have multiple rules for different types of events.
        // The eventType property would be used to find rules relevant to the event
        ageLimitRule.setEventType(Rule.eventType.ORDER);

        ageLimitRule.setConditions(Arrays.asList(ageOverLimit, ageUnderLimit));

        String drl = applyRuleTemplate(changeUserLimitEvent, ageLimitRule);
        AlertDecision alertDecision = evaluate(drl, changeUserLimitEvent, kieContainer);

        System.out.println(alertDecision.getDoAlert());

        // doAlert is false by default
        if (alertDecision.getDoAlert()) {
            System.out.println("Izvrsilo se");
            // do notification
        }

    }

    static private AlertDecision evaluate(String drl, Event event, KieContainer kieContainer) throws Exception {

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write("src/main/resources/dynamic/rule.drl", drl);
        kieServices.newKieBuilder(kieFileSystem).buildAll();

//        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("dynamicRuleKSession");

        AlertDecision alertDecision = new AlertDecision();
        statelessKieSession.getGlobals().set("alertDecision", alertDecision);
        statelessKieSession.execute(event);

        return alertDecision;
    }

    static private String applyRuleTemplate(ChangeUserLimitEvent event, Rule myRule) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();

        data.put("myRule", myRule);
        data.put("eventType", event.getClass().getName());

        return objectDataCompiler.compile(Arrays.asList(data),
                Thread.currentThread().getContextClassLoader().getResourceAsStream("rules/dynamic/dynamic-rules.drl"));
    }
}
