package live.healthy.service.basic;

import live.healthy.exception.user.BodyTypeNotFound;
import live.healthy.exception.user.UserNotFound;
import live.healthy.facts.dto.BodyInfoDto;
import live.healthy.facts.dto.BodyTypeDto;
import org.springframework.stereotype.Service;

@Service
public interface BasicDeterminationService {

    /**
     * Method used to determine which body type registered user is.
     *
     * @param bodyInfoDto
     * @param userId
     * @return
     * @throws BodyTypeNotFound
     * @throws UserNotFound
     */
    BodyTypeDto determine(BodyInfoDto bodyInfoDto, Long userId) throws BodyTypeNotFound, UserNotFound;
}
