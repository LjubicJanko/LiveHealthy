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
public class IntakeSubmitEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Long userId;
    private IntakeSubmitType submitType;


    public IntakeSubmitEvent() {
        super();
    }

    public IntakeSubmitEvent(Long userId, IntakeSubmitType submitType) {
        super();
        this.executionTime = new Date();
        this.userId = userId;
        this.submitType = submitType;
    }
}
