package live.healthy.facts.model.body_desc_enums;

public enum Shoulders {
    NARROWER_THAN_HIPS("Narrower than my hips"),
    SAME_AS_HIPS("The same width as my hips"),
    WIDER_THAN_HIPS("Wider than my hips");

    private String value;

    Shoulders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
