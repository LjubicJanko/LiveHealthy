package live.healthy.facts.model.body_desc_enums;

public enum BodyLook {
    LONG_AND_NARROW("Long and narrow"),
    SQUARE_AND_RUGGED("Square and rugged"),
    ROUND_AND_SOFT("Round and soft");

    private String value;

    BodyLook(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
