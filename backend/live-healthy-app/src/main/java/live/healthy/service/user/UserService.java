package live.healthy.service.user;

import live.healthy.exception.user.*;
import live.healthy.facts.dto.UserDTO;
import live.healthy.facts.dto.UserEditDTO;
import live.healthy.facts.dto.UserRegistrationDTO;
import live.healthy.facts.dto.UserWithAuthoritiesDTO;
import live.healthy.facts.model.user.AbstractUser;
import live.healthy.facts.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User create(UserRegistrationDTO userRegistrationDTO) throws UsernameAlreadyExist, UsernameNotValid, PasswordNotValid, EmailNotValid, EmailAlreadyExist, AuthorityDoesNotExist, UserAgeNotValid;

    AbstractUser findByUsername(String username) throws UserNotFound;

    User editUser(UserEditDTO userEditDTO, String username) throws UserNotFound, EmailNotValid, FirstNameNotValid, LastNameNotValid;

    UserWithAuthoritiesDTO get(Long id) throws UserNotFound;
}
