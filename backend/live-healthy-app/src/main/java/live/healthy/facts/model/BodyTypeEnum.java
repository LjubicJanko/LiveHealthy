package live.healthy.facts.model;

public enum BodyTypeEnum {
    ECTOMORPH("ECTOMORPH"), MESOMORPH("MESOMORPH"), ENDOMORPH("ENDOMORPH"),
    ECTOMORPH_MESOMORPH("ECTOMORPH_MESOMORPH"), ENDOMORPH_MESOMORPH("ECTOMORPH_MESOMORPH"),
    NONEXISTENT("NONEXISTENT");

    private String value;

    BodyTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}