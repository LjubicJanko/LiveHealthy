package live.healthy.facts.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyInfoDto {

	@NotNull
	private String shoulders;
	@NotNull
	private String forearms;
	@NotNull
	private String bodyTendations;
	@NotNull
	private String bodyLook;
	@NotNull
	private String weightTendations;
	@NotNull
	private boolean sex;
}
