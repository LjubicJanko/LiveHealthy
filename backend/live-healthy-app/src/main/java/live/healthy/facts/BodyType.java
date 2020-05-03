package live.healthy.facts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class BodyType {

    private enum Type {
        ECTOMORPH, MESOMORPH, ENDOMORPH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Type type;
    @NotBlank
    private String description;
    @NotNull
    private double bmi;
}
