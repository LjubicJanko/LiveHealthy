package live.healthy.facts.model;

public enum BodyTypeEnum {
    ECTOMORPH("ECTOMORPH"), MESOMORPH("MESOMORPH"), ENDOMORPH("ENDOMORPH");

    private String value;

    BodyTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}