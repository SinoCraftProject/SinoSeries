package games.moegirl.sinocraft.sinocore.event;

public enum EventPriority {
    HIGH(3),
    NORMAL(2),
    LOW(1),
    ;

    private final int value;

    EventPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
