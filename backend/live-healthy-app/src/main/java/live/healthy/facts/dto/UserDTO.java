package live.healthy.facts.dto;

import live.healthy.facts.model.plan.NutritionPlan;
import live.healthy.facts.model.plan.TrainingPlan;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;

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


    private NutritionPlanDto nutritionPlan;
    private TrainingPlanDto trainingPlan;

}