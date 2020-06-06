package live.healthy.events;

public enum IntakeSubmitType {
    REGULAR("REGULAR"),
    IRREGULAR("IRREGULAR"),
    REWARDED_NOT_NEEDED("REWARDED_NOT_NEEDED"),
    REGISTERED_NOT_NEEDED("REGISTERED_NOT_NEEDED"),
    NONE("NONE");

    private String value;

    IntakeSubmitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
