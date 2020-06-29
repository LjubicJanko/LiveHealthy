package live.healthy.facts.model.body_desc_enums;

public enum BodyTendations {
    SKINNY("Stay skinny"),
    LEAN_YET_MUSCULAR("Stay lean, yet muscular"),
    CARRY_EXTRA_FAT("Carry a bit of extra fat");

    private String value;

    BodyTendations(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
