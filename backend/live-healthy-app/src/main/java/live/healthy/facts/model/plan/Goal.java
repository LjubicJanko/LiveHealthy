package live.healthy.facts.model.plan;

public enum Goal {
    WEIGHT_LOSS("WEIGHT_LOSS"), MILD_WEIGHT_LOSS("MILD_WEIGHT_LOSS"), EXTREME_WEIGHT_LOSS("EXTREME_WEIGHT_LOSS"),
    MAINTAIN_WEIGHT("MAINTAIN_WEIGHT"),
    WEIGHT_GAIN("WEIGHT_GAIN"), MILD_WEIGHT_GAIN("MILD_WEIGHT_GAIN"), EXTREME_WEIGHT_GAIN("EXTREME_WEIGHT_GAIN");

    private String value;

    Goal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
