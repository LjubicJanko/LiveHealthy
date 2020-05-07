package live.healthy.facts.dto;

import live.healthy.facts.BodyType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private double height;
    private double weight;
    private String bodyType;

}