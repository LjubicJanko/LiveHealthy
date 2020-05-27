package live.healthy.facts.model;

public enum Type {
    ECTOMORPH("ECTOMORPH"), MESOMORPH("MESOMORPH"), ENDOMORPH("ENDOMORPH");

    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}