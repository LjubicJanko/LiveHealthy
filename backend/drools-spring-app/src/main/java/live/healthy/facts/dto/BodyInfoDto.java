package live.healthy.facts.dto;

import javax.validation.constraints.NotNull;

import live.healthy.facts.DailyActivity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyInfoDto {

	@NotNull
	private int bodyWeight;
	@NotNull
	private int bodyHeight;
	@NotNull
	private int age;
	@NotNull
	private boolean sex;
	@NotNull
	private DailyActivity dailyActivity;
}
