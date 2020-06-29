package live.healthy.facts.model.body_desc_enums;

public enum WeightTendations {
    CAN_NOT_GAIN("Have trouble gaining weight in the form of muscle or fat"),
    CAN_GAIN_AND_LOSE("I can gain and lose without too much of a struggle"),
    CAN_GAIN_CAN_NOT_LOSE("Gain weight easily, but find it hard to lose");

    private String value;

    WeightTendations(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
