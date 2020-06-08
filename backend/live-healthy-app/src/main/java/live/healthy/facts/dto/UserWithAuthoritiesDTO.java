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
    private double height;
    private double weight;
    private String bodyType;
    private boolean sex;


    private NutritionPlanDto nutritionPlan;
    private List<Authority> authorities;


}