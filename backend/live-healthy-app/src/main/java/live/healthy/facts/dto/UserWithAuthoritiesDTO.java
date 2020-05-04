package live.healthy.facts.dto;

import live.healthy.facts.model.user.Authority;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserWithAuthoritiesDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private double weight;
    private double height;
    private List<Authority> authorities;


}