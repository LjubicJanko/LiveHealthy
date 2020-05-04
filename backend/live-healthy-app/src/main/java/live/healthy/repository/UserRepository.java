package live.healthy.repository;

import live.healthy.facts.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByEmail(String email);
}

