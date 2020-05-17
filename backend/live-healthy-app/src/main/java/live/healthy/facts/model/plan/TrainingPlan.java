package live.healthy.facts.model.plan;

import live.healthy.facts.model.training.Exercise;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class TrainingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull
    public Goal goal;

    @ManyToMany
    @JoinTable(
            name = "weekly_training",
            joinColumns = @JoinColumn(name = "training_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "daily_training_id"))
    private Set<DailyTraining> weeklyPlan;
    @OneToMany
    @JoinTable(
            name = "forbidden_training",
            joinColumns = @JoinColumn(name = "training_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "forbidden_training_id"))
    private Set<Exercise> forbiddenExercises;
}
