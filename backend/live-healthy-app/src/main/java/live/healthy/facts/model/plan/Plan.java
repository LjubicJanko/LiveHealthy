package live.healthy.facts.model.plan;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public abstract class Plan {

    private enum Goal {
        WEIGHT_LOSS, MILD_WEIGHT_LOSS, EXTREME_WEIGHT_LOSS,
        MAINTAIN_WEIGHT,
        WEIGHT_GAIN, MILD_WEIGHT_GAIN, EXTREME_WEIGHT_GAIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Goal goal;
    @NotNull
    private double startingBmi;
    @NotNull
    private double startingBmr;
    @NotNull
    private double startingWeight;
}
