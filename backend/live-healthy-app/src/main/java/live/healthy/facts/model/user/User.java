package live.healthy.facts.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.healthy.facts.model.BodyType;
import live.healthy.facts.model.plan.NutritionPlan;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user")
public class User extends AbstractUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    public boolean sex;

    @NotNull
    public int age;

    @NotNull
    public double height;

    @NotNull
    public double weight;

    @ManyToOne
    public BodyType bodyType;

    public double startingBmi;

    public double startingBmr;

    public double startingBfp;

    public double startingWeight;

    public double idealBodyWeight;


    @ManyToOne
    private NutritionPlan nutritionPlan;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @NotBlank
    @Column(name = "email")
    private String email;

    @Column(name = "enabled")
    private boolean enabled;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public User(){
        super();
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull boolean sex) {
        super(username, password, firstName, lastName);
        this.email = email;
        this.sex = sex;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String firstName, @NotNull String lastName, @NotNull String email,
                @NotNull int age, @NotNull double height, @NotNull double weight, @NotNull boolean sex) {
        super(username, password, firstName, lastName);
        this.email = email;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
