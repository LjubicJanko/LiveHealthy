package live.healthy.service.dynamic;

import live.healthy.facts.dto.CreateBodyTypeRuleDto;

public interface DynamicService {

    /**
     * Method used to create rule for body type determination.
     * <p>
     * After rule is created all users are being processed and their body types are changed accordingly.
     *
     * @param createBodyTypeRuleDto
     */
    String createBodyTypeRule(CreateBodyTypeRuleDto createBodyTypeRuleDto);
}
