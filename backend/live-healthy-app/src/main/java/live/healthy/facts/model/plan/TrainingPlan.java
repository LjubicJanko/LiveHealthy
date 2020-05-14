package live.healthy.facts.model.plan;

import live.healthy.facts.model.training.Exercise;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class TrainingPlan extends Plan {
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
