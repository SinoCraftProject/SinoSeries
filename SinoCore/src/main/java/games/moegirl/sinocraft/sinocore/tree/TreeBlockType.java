package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

import static games.moegirl.sinocraft.sinocore.utility.Functions.*;

public enum TreeBlockType {
    LOG("log", lExt(TreeUtilities::rotatedPillar), ext(TreeUtilities::logProp), TreeUtilities::blockItem),
    STRIPPED_LOG("stripped_log", lExt(TreeUtilities::rotatedPillar), ext(TreeUtilities::logProp), TreeUtilities::blockItem),
    LOG_WOOD("log_wood", lExt(TreeUtilities::rotatedPillar), ext(TreeUtilities::woodProp), TreeUtilities::blockItem),
    STRIPPED_LOG_WOOD("stripped_log_wood", lExt(TreeUtilities::rotatedPillar), ext(TreeUtilities::woodProp), TreeUtilities::blockItem),
    SAPLING("sapling", TreeUtilities::sapling, ext(TreeUtilities::saplingProp), TreeUtilities::blockItem),
    POTTED_SAPLING("potted_sapling", TreeUtilities::pottedSapling, ext(TreeUtilities::pottedSaplingProp)),
    PLANKS("planks", lExt(TreeUtilities::normalBlock), ext(TreeUtilities::planksProp), TreeUtilities::blockItem),
    LEAVES("leaves", lExt(TreeUtilities::leaves), ext(TreeUtilities::leavesProp), TreeUtilities::blockItem),
    STAIRS("stairs", TreeUtilities::stairs, ext(TreeUtilities::stairsProp), TreeUtilities::blockItem),
    SLAB("slab", lExt(TreeUtilities::slab), ext(TreeUtilities::slabProp), TreeUtilities::blockItem),
    BUTTON("button", TreeUtilities::button, ext(TreeUtilities::buttonProp), TreeUtilities::blockItem),
    DOOR("door", TreeUtilities::door, ext(TreeUtilities::doorProp), TreeUtilities::doubleBlockItem),
    TRAPDOOR("trapdoor", TreeUtilities::trapdoor, ext(TreeUtilities::trapdoorProp), TreeUtilities::blockItem),
    FENCE("fence", lExt(TreeUtilities::fence), ext(TreeUtilities::fenceProp), TreeUtilities::blockItem),
    FENCE_GATE("fence_gate", TreeUtilities::fenceGate, ext(TreeUtilities::fenceProp), TreeUtilities::blockItem),
    PRESSURE_PLATE("pressure_gate", TreeUtilities::pressurePlate, ext(TreeUtilities::pressurePlateProp), TreeUtilities::blockItem),
    SIGN("sign", TreeUtilities::standingSign, ext(TreeUtilities::signProp), rExt(TreeUtilities::signBlockItem)),
    WALL_SIGN("wall_sign", TreeUtilities::wallSign, TreeUtilities::wallSignProp),
    HANGING_SIGN("hanging_sign", TreeUtilities::ceilingHangingSign, ext(TreeUtilities::hangingSignProp), rExt(TreeUtilities::hangingSignBlockItem)),
    WALL_HANGING_SIGN("wall_hanging_sign", TreeUtilities::wallHangingSign, TreeUtilities::wallHangingSignProp),
    BOAT("boat"),   // Todo: qyl27: not implemented yet.
    CHEST_BOAT("chest_boat"),   // Todo: qyl27: not implemented yet.
    ;

    private final String name;
    private final boolean hasBlock;
    private final boolean hasItem;

    public final BiFunction<Tree, BlockBehaviour.Properties, Block> defBlock;
    public final Function<Tree, BlockBehaviour.Properties> defBlockProp;
    public final BiFunction<Tree, TreeBlockType, Item> defItem;

    /**
     * A Tree Block Type.
     *
     * @param name     Type name.
     * @param hasItem  Has item?
     * @param hasBlock Has block?
     */
    TreeBlockType(String name, boolean hasBlock, boolean hasItem,
                  BiFunction<Tree, BlockBehaviour.Properties, Block> defBlock,
                  Function<Tree, BlockBehaviour.Properties> defBlockProp,
                  BiFunction<Tree, TreeBlockType, Item> defItem) {
        this.name = name;
        this.hasBlock = hasBlock;
        this.hasItem = hasItem;
        this.defBlock = defBlock;
        this.defBlockProp = defBlockProp;
        this.defItem = defItem;
    }

    TreeBlockType(String name,
                  BiFunction<Tree, BlockBehaviour.Properties, Block> defBlock,
                  Function<Tree, BlockBehaviour.Properties> defBlockProp,
                  BiFunction<Tree, TreeBlockType, Item> defItem) {
        this(name, true, true, defBlock, defBlockProp, defItem);
    }

    TreeBlockType(String name, BiFunction<Tree, TreeBlockType, Item> defItem) {
        this(name, false, true,
                (tree, v2) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't have a " + name + "block");
                },
                tree -> {
                    throw new RuntimeException("Tree " + tree.name + " don't have a " + name + "block");
                },
                defItem);
    }

    TreeBlockType(String name,
                  BiFunction<Tree, BlockBehaviour.Properties, Block> defBlock,
                  Function<Tree, BlockBehaviour.Properties> defBlockProp) {
        this(name, true, false, defBlock, defBlockProp,
                (tree, type) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't have a " + name + "item");
                });
    }

    TreeBlockType(String name) {
        this(name, false, false,
                (tree, v2) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't have a " + name + "block");
                },
                tree -> {
                    throw new RuntimeException("Tree " + tree.name + " don't have a " + name + "block");
                },
                (tree, v2) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't have a " + name + "item");
                });
    }

    public String getName() {
        return name;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public boolean hasBlock() {
        return hasBlock;
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
