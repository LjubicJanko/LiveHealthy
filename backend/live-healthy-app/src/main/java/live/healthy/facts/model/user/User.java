package live.healthy.facts.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.healthy.facts.model.BodyType;
import live.healthy.facts.model.plan.NutritionPlan;
import lombok.Data;
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
@Data
public class User implements UserDetails {

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

    @NotNull
    private String username;

    @NotNull
    protected String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

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


    private String encodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

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

    public void setPassword(String password) {
        Timestamp now = new Timestamp(DateTime.now().getMillis());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull boolean sex) {
        this.username = username;
        this.password = this.encodePassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String firstName, @NotNull String lastName, @NotNull String email,
                @NotNull int age, @NotNull double height, @NotNull double weight, @NotNull boolean sex) {
        this.username = username;
        this.password = this.encodePassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
