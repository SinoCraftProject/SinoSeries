package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.resources.ResourceLocation;

public enum TreeBlockType {
    LOG("log", true, false, true),
    STRIPPED_LOG("stripped_log", true, false, true),
    LOG_WOOD("log_wood", true, false, true),
    STRIPPED_LOG_WOOD("stripped_log_wood", true, false, true),
    SAPLING("sapling", true, false, false),
    POTTED_SAPLING("potted_sapling", false, false, false),
    PLANKS("planks", true, false, true),
    LEAVES("leaves", true, false, true),
    STAIRS("stairs", true, false, false),
    SLAB("slab", true, false, true),
    BUTTON("button", true, false, true),
    DOOR("door", true, false, true),
    TRAPDOOR("trapdoor", true, false, true),
    FENCE("fence", true, false, true),
    FENCE_GATE("fence_gate", true, false, true),
    PRESSURE_PLATE("pressure_gate", true, false, true),
    SIGN("sign", true, false, false),
    WALL_SIGN("wall_sign", false, false, false),
    HANGING_SIGN("hanging_sign", true, false, false),
    WALL_HANGING_SIGN("wall_hanging_sign", false, false, false),
    BOAT("boat", false, true, false),   // Todo: qyl27: not implemented yet.
    CHEST_BOAT("chest_boat", false, true, false),   // Todo: qyl27: not implemented yet.
    ;

    private final String name;
    private final boolean hasNoBlock;
    private final boolean hasItem;
    private final boolean general;

    /**
     * A Tree Block Type.
     * @param name Type name.
     * @param hasItem Has item?
     * @param hasNoBlock Has item but no block?
     * @param general Constructor needs only BlockBehavior.Properties?
     */
    private TreeBlockType(String name, boolean hasItem, boolean hasNoBlock, boolean general) {
        this.name = name;
        this.hasNoBlock = hasNoBlock;
        this.hasItem = hasItem;
        this.general = general;
    }

    public String getName() {
        return name;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public boolean hasNoBlock() {
        return hasNoBlock;
    }

    public boolean isGeneralBlock() {
        return general;
    }

    public String makeRegistryName(String name) {
        return name + "_" + getName();
    }

    public String makeRegistryName(ResourceLocation name) {
        return makeRegistryName(name.getPath());
    }

    public ResourceLocation makeResourceLoc(ResourceLocation name) {
        return new ResourceLocation(name.getNamespace(), name.getPath() + "_" + getName());
    }
}
