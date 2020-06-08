package live.healthy.service.user;

import live.healthy.common.util.BodyIndexUtil;
import live.healthy.exception.user.*;
import live.healthy.facts.dto.UserDTO;
import live.healthy.facts.dto.UserEditDTO;
import live.healthy.facts.dto.UserRegistrationDTO;
import live.healthy.facts.dto.UserWithAuthoritiesDTO;
import live.healthy.facts.model.user.AbstractUser;
import live.healthy.facts.model.user.Admin;
import live.healthy.facts.model.user.Authority;
import live.healthy.facts.model.user.User;
import live.healthy.repository.AuthorityRepository;
import live.healthy.repository.user.AdminRepository;
import live.healthy.repository.user.UserRepository;
import live.healthy.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static live.healthy.config.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private AuthorityRepository authorityRepository;

    public UserServiceImpl(UserRepository userRepository, AdminRepository adminRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public User create(UserRegistrationDTO userRegistrationDTO) throws UsernameAlreadyExist, UsernameNotValid, PasswordNotValid, EmailNotValid, EmailAlreadyExist, AuthorityDoesNotExist, UserAgeNotValid {
        Optional<User> userFound = userRepository.findOneByUsername(userRegistrationDTO.getUsername());
        Optional<Admin> adminFound = adminRepository.findOneByUsername(userRegistrationDTO.getUsername());

        if (userFound.isPresent() || adminFound.isPresent()) {
            throw new UsernameAlreadyExist();
        }

        userFound = userRepository.findOneByEmail(userRegistrationDTO.getEmail());

        if (userFound.isPresent()) {
            throw new EmailAlreadyExist();
        }

        if (!userRegistrationDTO.getUsername().matches(USERNAME_REGEX)) {
            throw new UsernameNotValid();
        }

        if (!userRegistrationDTO.getPassword().matches(PASSWORD_REGEX)) {
            throw new PasswordNotValid();
        }

        if (!userRegistrationDTO.getEmail().matches(EMAIL_REGEX)) {
            throw new EmailNotValid();
        }

        if (userRegistrationDTO.getAge() <= 0) {
            throw new UserAgeNotValid();
        }

        User newUser = new User(userRegistrationDTO.getUsername(),
                userRegistrationDTO.getPassword(),
                userRegistrationDTO.getFirstName(),
                userRegistrationDTO.getLastName(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getAge(),
                userRegistrationDTO.getHeight(),
                userRegistrationDTO.getWeight(),
                userRegistrationDTO.isSex());

        List<Authority> authorities = new ArrayList<Authority>();

        Optional<Authority> authority = authorityRepository.findOneByName("ROLE_REGISTERED");
        if (!authority.isPresent()) {
            throw new AuthorityDoesNotExist("ROLE_REGISTERED");
        }

        double bmi = BodyIndexUtil.bodyMassIndex(newUser.getHeight(), newUser.getWeight());
        newUser.setStartingBmi(bmi);
        newUser.setStartingBfp(BodyIndexUtil.bodyFatPercentage(bmi, newUser.getAge(), newUser.isSex()));
        newUser.setStartingBmr(BodyIndexUtil.basalMetabolicRate(newUser.getHeight(), newUser.getWeight()
                ,newUser.isSex(), newUser.getAge()));
        newUser.setIdealBodyWeight(BodyIndexUtil.idealBodyWeight(newUser.getHeight(), newUser.getWeight()
                ,newUser.isSex()));
        newUser.setStartingWeight(newUser.getWeight());


        userRepository.save(newUser);

        authorities.add(authority.get());
        newUser.setAuthorities(authorities);

        return userRepository.save((User) newUser);
    }

    @Override
    public AbstractUser findByUsername(String username) throws UserNotFound{
        Optional<User> u = userRepository.findOneByUsername(username);
        if (u.isPresent()) {
            return u.get();
        } else {
            Optional<Admin> a = adminRepository.findOneByUsername(username);
            if (a.isPresent()) {
                return a.get();
            }
            throw new UserNotFound();
        }
    }

    @Override
    public User editUser(UserEditDTO userEditDTO, String username) throws UserNotFound, EmailNotValid, FirstNameNotValid, LastNameNotValid {

        Optional<User> userOptional = userRepository.findOneByUsername(username);

        if (!userOptional.isPresent()) {
            throw new UserNotFound();
        } else {
            User user = userOptional.get();
            if (userEditDTO.getEmail() != null) {
                if (!userEditDTO.getEmail().matches(EMAIL_REGEX)) {
                    throw new EmailNotValid();
                }
                user.setEmail(userEditDTO.getEmail());
            }

            if (userEditDTO.getFirstName() != null) {
                if (userEditDTO.getFirstName().matches(WHITESPACES_REGEX)) {
                    throw new FirstNameNotValid();
                }
                user.setFirstName(userEditDTO.getFirstName());
            }

            if (userEditDTO.getLastName() != null) {
                if (userEditDTO.getLastName().matches(WHITESPACES_REGEX)) {
                    throw new LastNameNotValid();
                }
                user.setLastName(userEditDTO.getLastName());
            }

            userRepository.save(user);
            return user;
        }

    }

    @Override
    public UserWithAuthoritiesDTO get(Long id) throws UserNotFound {
        User user = userRepository.findById(id).orElseThrow(UserNotFound::new);
        UserWithAuthoritiesDTO userWithAuthoritiesDTO = ObjectMapperUtils.map(user, UserWithAuthoritiesDTO.class);   // separated from return because of debug
        if(user.getBodyType() != null) {
            userWithAuthoritiesDTO.setBodyType(user.getBodyType().getBodyTypeEnum());
        }
        return userWithAuthoritiesDTO;
    }


}
