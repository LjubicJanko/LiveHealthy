package live.healthy.repository.user;

import live.healthy.facts.model.BodyType;
import live.healthy.facts.model.BodyTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {
    Optional<BodyType> findByDescription(String description);

}
