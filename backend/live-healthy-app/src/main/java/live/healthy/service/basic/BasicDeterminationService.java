package live.healthy.service.basic;

import live.healthy.exception.user.BodyTypeNotFound;
import live.healthy.exception.user.UserNotFound;
import org.springframework.stereotype.Service;

import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;

@Service
public interface BasicDeterminationService {
	
	BodyTypeDto determine(BodyInfoDto bodyInfoDto, Long userId) throws BodyTypeNotFound, UserNotFound;
}
