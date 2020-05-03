package live.healthy.facts.model.user;

import live.healthy.facts.BodyType;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private int age;
    @NotNull
    private double height;
    @NotNull
    private double weight;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @Nullable
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BodyType bodyType;
}
