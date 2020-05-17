package live.healthy.facts.model;

import live.healthy.facts.model.AbstractUser;
import live.healthy.facts.model.Type;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class BodyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    public Type type;
    @NotBlank
    private String description;
    @NotNull
    private double bmi;

    public String getType(){
        return this.type.name();
    }
}
