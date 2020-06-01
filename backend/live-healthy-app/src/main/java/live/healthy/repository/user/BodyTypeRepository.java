package live.healthy.repository.user;

import live.healthy.facts.model.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {
    Optional<BodyType> findByDescription(String description);
}
