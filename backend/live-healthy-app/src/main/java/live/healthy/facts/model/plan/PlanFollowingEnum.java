package live.healthy.facts.model.plan;

public enum PlanFollowingEnum {

    REGULAR("REGULAR"),
    REWARDED("REWARDED"),
    PUNISHED("PUNISHED");

    private String value;

    PlanFollowingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
