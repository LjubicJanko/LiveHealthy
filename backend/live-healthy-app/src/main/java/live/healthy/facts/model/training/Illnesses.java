package live.healthy.facts.model.training;

public enum Illnesses {
    HEADACHES("HEADACHES"),
    HEART_DISEASE("HEART_DISEASE"),
    ARM_INJURY("ARM_INJURY"),
    LEG_INJURY("LEG_INJURY");

    private String value;

    Illnesses(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
