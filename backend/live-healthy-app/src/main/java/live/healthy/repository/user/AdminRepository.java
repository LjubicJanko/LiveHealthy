package live.healthy.repository.user;

import live.healthy.facts.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findOneByUsername(String username);
}
