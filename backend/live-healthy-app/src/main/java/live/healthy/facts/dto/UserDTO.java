package live.healthy.facts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean sex;

}