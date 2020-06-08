package live.healthy.repository.user;

import live.healthy.facts.model.user.BodyDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BodyDescriptionRepository extends JpaRepository<BodyDescription, Long> {
}
