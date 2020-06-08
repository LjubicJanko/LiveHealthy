package live.healthy.facts.dto;

import lombok.Data;

@Data
public class CreateRuleDto {

    private boolean ageFieldIncluded;
//    private boolean weightFieldIncluded;

    private int ageLowLimit;
    private int ageHighLimit;
}
