package live.healthy.facts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {

    @NotBlank
    private String foodGroup;
    @NotBlank
    private String description;
    @NotNull
    private int energy_kcal;
    @NotNull
    private int proteins;
    @NotNull
    private double fats;
    @NotNull
    private double carbs;
}
