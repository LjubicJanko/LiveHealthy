package live.healthy.facts.model.body_desc_enums;

public enum Forearms {
    SMALL("Small"),
    AVERAGE("Average"),
    BIG("Big");

    private String value;

    Forearms(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
