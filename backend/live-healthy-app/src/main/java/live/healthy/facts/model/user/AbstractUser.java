package live.healthy.facts.model.user;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Data
@AllArgsConstructor
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    protected String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    public AbstractUser(@NotNull String username, @NotNull String password, @NotNull String firstName, @NotNull String lastName) {
        this.username = username;
        this.password = this.encodePassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AbstractUser() {

    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(DateTime.now().getMillis());
        this.setLastPasswordResetDate( now );
        this.password = password;
    }

    private String encodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
