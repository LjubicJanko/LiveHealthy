package live.healthy.service.basic;

import org.springframework.stereotype.Service;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;

@Service
public interface BasicDeterminationService {
	
	BodyTypeDto determine(BodyInfoDto bodyInfoDto);
}
