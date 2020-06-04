package live.healthy.events;

import lombok.Data;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.io.Serializable;
import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("24h")
@Data
public class CreateRewardEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Long userId;
    private Long nutritionPlanId;


    public CreateRewardEvent() {
        super();
    }

    public CreateRewardEvent(Long userId, Long nutritionPlanId) {
        super();
        this.executionTime = new Date();
        this.userId = userId;
        this.nutritionPlanId = nutritionPlanId;
    }
}
