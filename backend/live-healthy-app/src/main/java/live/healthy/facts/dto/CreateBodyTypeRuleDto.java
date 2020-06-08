package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBodyTypeRuleDto {

    private String shoulders;
    private String forearms;
    private String bodyTendations;
    private String bodyLook;
    private String weightTendations;

    private String bodyType;
}
