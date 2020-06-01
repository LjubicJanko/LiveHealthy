package live.healthy.common.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

import static live.healthy.common.constants.Constants.*;

@Component
public class BodyIndexUtil {

    /**
     * private constructor, no instantiation
     */
    private BodyIndexUtil() {
    }

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    /**
     * How is it calculated? Square your height in meters. Divide your weight in kilograms by the number you obtained.
     * For example: your height is 170cm; your weight is 65kg. Therefore, 65 : (1.7 * 1.7) = 22.5
     *
     * @param height -	body height
     * @param weight -	body weight
     * @return
     */
    public static double bodyMassIndex(double height, double weight) {
        double heightInMetersSquared = Math.pow(height / 100, 2);
        return Double.parseDouble(df2.format(weight / heightInMetersSquared));
    }

    /**
     * Calculates ideal body weight by D. R. Miller formula from 1983.
     * Calculating is different for male and female
     *
     * @param height
     * @param weight
     * @return
     */
    public static double idealBodyWeight(double height, double weight, boolean sex) {
        double baseWeight;
        double heightInInchesOverFiveFeet = 0;
        double ibw;
        double heightInFeet = height / FEET_COEFFICIENT;


        if (heightInFeet > 5) {
            heightInInchesOverFiveFeet = 12 * (heightInFeet - 5);
        }
        if (sex) {
            baseWeight = 56.2;
            ibw = baseWeight + heightInInchesOverFiveFeet * MALE_IBW_COEFFICIENT;
        } else {
            baseWeight = 53.1;
            ibw = baseWeight + heightInInchesOverFiveFeet * FEMALE_IBW_COEFFICIENT;
        }
        return Double.parseDouble(df2.format(ibw));
    }

    /**
     * Calculates percentage of fat in body based on bmi, age and sex
     *
     * @param bmi
     * @param age
     * @param sex
     * @return
     */
    public static double bodyFatPercentage(double bmi, double age, boolean sex) {
        double bfp = 0;
        if (sex) {
            bfp = BFP_BMI_COEFFICIENT * bmi + BFP_AGE_COEFFICIENT * age - BFP_MALE_COEFFICIENT;
        } else {
            bfp = BFP_BMI_COEFFICIENT * bmi + BFP_AGE_COEFFICIENT * age - BFP_FEMALE_COEFFICIENT;
        }
        return Double.parseDouble(df2.format(bfp));
    }

    /**
     * Calculates amount of calories burned by body in inactive state during one day.
     *
     * @param height
     * @param weight
     * @param sex
     * @param age
     * @return
     */
    public static double basalMetabolicRate(double height, double weight, boolean sex, int age) {
        double bmr = 0.0;
        if (sex) {
            bmr = (((MALE_BMR_WEIGHT_COEFFICIENT * weight) + (MALE_BMR_HEIGHT_COEFFICIENT * height))
                    - (MALE_BMR_AGE_COEFFICIENT * age)) + MALE_BMR_COEFFICIENT;
        } else {
            bmr = (((FEMALE_BMR_WEIGHT_COEFFICIENT * weight) + (FEMALE_BMR_HEIGHT_COEFFICIENT * height))
                    - (FEMALE_BMR_AGE_COEFFICIENT * age)) + FEMALE_BMR_COEFFICIENT;
        }
        return Double.parseDouble(df2.format(bmr));
    }
}
