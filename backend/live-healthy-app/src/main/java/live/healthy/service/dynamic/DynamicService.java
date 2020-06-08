package live.healthy.service.dynamic;

import live.healthy.facts.dto.CreateRuleDto;

public interface DynamicService {

    void createRule(CreateRuleDto createRuleDto) throws Exception;
}
