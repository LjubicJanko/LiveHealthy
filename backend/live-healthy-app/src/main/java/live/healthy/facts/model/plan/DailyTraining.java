package live.healthy.facts.model.plan;

import live.healthy.facts.model.training.Exercise;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class DailyTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "daily_exercise",
            joinColumns = @JoinColumn(name = "daily_training_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private Set<Exercise> exerciseList;
    @NotNull
    private double caloriesGoal;
    @ManyToMany
//    @JoinTable(
//            name = "weekly_training",
//            joinColumns = @JoinColumn(name = "daily_training_id"),
//            inverseJoinColumns = @JoinColumn(name = "training_plan_id"))
    private Set<TrainingPlan> trainingPlans;
}
