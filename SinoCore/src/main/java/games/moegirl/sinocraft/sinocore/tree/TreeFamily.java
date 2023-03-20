package games.moegirl.sinocraft.sinocore.tree;

public enum TreeFamily {
    LOG("log"),
    STRIPPED_LOG("stripped_log"),
    LOG_BARK("log_bark"),
    STRIPPED_LOG_BARK("stripped_log_bark"),

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
    WALL_SIGN("wall_sign"),

    SAPLING("sapling")
    ;

    private final String name;

    private TreeFamily(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
