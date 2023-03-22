package games.moegirl.sinocraft.sinocore.tree;

public enum TreeBlockType {
    LOG("log"),
    STRIPPED_LOG("stripped_log"),
    LOG_BARK("log_bark"),
    STRIPPED_LOG_BARK("stripped_log_bark"),

    SAPLING("sapling"),
    PLANKS("planks"),
    LEAVES("leaves"),

    STAIRS("stairs"),
    SLAB("slab"),
    BUTTON("button"),
    DOOR("door"),
    TRAPDOOR("trapdoor"),
    FENCE("fence"),
    FENCE_GATE("fence_gate"),
    PRESSURE_PLATE("pressure_gate"),

    SIGN("sign"),
    HANGING_SIGN("hanging_sign"),

    BOAT("boat"),
    CHEST_BOAT("chest_boat"),
    ;

    private final String name;

    private TreeBlockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
