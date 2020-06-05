package live.healthy.events;

public enum IntakeSubmitType {
    REGULAR("REGULAR"),
    IRREGULAR("IRREGULAR"),
    NONE("NONE"),
    REWARDED_NOT_NEEDED("REWARDED_NOT_NEEDED");

    private String value;

    IntakeSubmitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}