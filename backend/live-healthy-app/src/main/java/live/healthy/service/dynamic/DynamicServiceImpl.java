package live.healthy.service.dynamic;

import live.healthy.facts.dto.CreateBodyTypeRuleDto;
import live.healthy.facts.dto.CreateRuleDto;
import live.healthy.facts.model.BodyType;
import live.healthy.facts.model.BodyTypeEnum;
import live.healthy.facts.model.user.BodyDescription;
import live.healthy.facts.model.user.User;
import live.healthy.repository.user.BodyTypeRepository;
import live.healthy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicServiceImpl implements DynamicService {

    private final KieContainer kieContainer;
    private final UserRepository userRepository;
    private final BodyTypeRepository bodyTypeRepository;

    @Override
    public String createBodyTypeRule(CreateBodyTypeRuleDto createBodyTypeRuleDto) {
        InputStream template = DynamicServiceImpl.class.
                getResourceAsStream("/rules/dynamic/body-type-dynamic-rule.drt");

        List<CreateBodyTypeRuleDto> data = new ArrayList<>();

        data.add(createBodyTypeRuleDto);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        System.out.println(drl);

        KieSession ksession = createKieSessionFromDRL(drl);

        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            ksession.insert(user);
        }

        ksession.fireAllRules();

        for (User user : allUsers) {
            if(!user.getDeterminatedBodyType().equals("")) {
                user.setBodyType(extractBodyType(BodyTypeEnum.valueOf(user.getDeterminatedBodyType())));
                userRepository.save(user);
            }
        }

        return drl;
    }

    private BodyType extractBodyType(BodyTypeEnum bodyTypeEnum) {
        List<BodyType> bodyTypes = bodyTypeRepository.findAll();
        for (BodyType bodyType: bodyTypes) {
            if (bodyType.getBodyTypeEnum().equals(bodyTypeEnum.getValue())) {
                return bodyType;
            }
        }
        return null;
    }

    private KieSession createKieSessionFromDRL(String drl) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: " + message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}
