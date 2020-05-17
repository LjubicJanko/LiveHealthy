package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {
    public String goal;
    public int fatLevelNeeded;  // -1 less fat, 0 normal, 1 more fat
}
