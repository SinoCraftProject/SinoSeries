package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

import static games.moegirl.sinocraft.sinocore.utility.Functions.*;

public enum TreeBlockType {
    LOG("log", lExt(TreeBlockUtilities::rotatedPillar), ext(TreeBlockUtilities::logProp), TreeBlockUtilities::blockItem),
    STRIPPED_LOG("stripped_log", lExt(TreeBlockUtilities::rotatedPillar), ext(TreeBlockUtilities::logProp), TreeBlockUtilities::blockItem),
    LOG_WOOD("log_wood", lExt(TreeBlockUtilities::rotatedPillar), ext(TreeBlockUtilities::woodProp), TreeBlockUtilities::blockItem),
    STRIPPED_LOG_WOOD("stripped_log_wood", lExt(TreeBlockUtilities::rotatedPillar), ext(TreeBlockUtilities::woodProp), TreeBlockUtilities::blockItem),
    SAPLING("sapling", TreeBlockUtilities::sapling, ext(TreeBlockUtilities::saplingProp), TreeBlockUtilities::blockItem),
    POTTED_SAPLING("potted_sapling", TreeBlockUtilities::pottedSapling, ext(TreeBlockUtilities::pottedSaplingProp)),
    PLANKS("planks", lExt(TreeBlockUtilities::normalBlock), ext(TreeBlockUtilities::planksProp), TreeBlockUtilities::blockItem),
    LEAVES("leaves", lExt(TreeBlockUtilities::leaves), ext(TreeBlockUtilities::leavesProp), TreeBlockUtilities::blockItem),
    STAIRS("stairs", TreeBlockUtilities::stairs, ext(TreeBlockUtilities::stairsProp), TreeBlockUtilities::blockItem),
    SLAB("slab", lExt(TreeBlockUtilities::slab), ext(TreeBlockUtilities::slabProp), TreeBlockUtilities::blockItem),
    BUTTON("button", TreeBlockUtilities::button, ext(TreeBlockUtilities::buttonProp), TreeBlockUtilities::blockItem),
    DOOR("door", TreeBlockUtilities::door, ext(TreeBlockUtilities::doorProp), TreeBlockUtilities::doubleBlockItem),
    TRAPDOOR("trapdoor", TreeBlockUtilities::trapdoor, ext(TreeBlockUtilities::trapdoorProp), TreeBlockUtilities::blockItem),
    FENCE("fence", lExt(TreeBlockUtilities::fence), ext(TreeBlockUtilities::fenceProp), TreeBlockUtilities::blockItem),
    FENCE_GATE("fence_gate", TreeBlockUtilities::fenceGate, ext(TreeBlockUtilities::fenceProp), TreeBlockUtilities::blockItem),
    PRESSURE_PLATE("pressure_gate", TreeBlockUtilities::pressurePlate, ext(TreeBlockUtilities::pressurePlateProp), TreeBlockUtilities::blockItem),
    SIGN("sign", TreeBlockUtilities::standingSign, ext(TreeBlockUtilities::signProp), rExt(TreeBlockUtilities::signBlockItem)),
    WALL_SIGN("wall_sign", TreeBlockUtilities::wallSign, TreeBlockUtilities::wallSignProp),
    HANGING_SIGN("hanging_sign", TreeBlockUtilities::ceilingHangingSign, ext(TreeBlockUtilities::hangingSignProp), rExt(TreeBlockUtilities::hangingSignBlockItem)),
    WALL_HANGING_SIGN("wall_hanging_sign", TreeBlockUtilities::wallHangingSign, TreeBlockUtilities::wallHangingSignProp),
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
                    throw new RuntimeException("Tree " + tree.name + " don't hava " + name + "block");
                },
                tree -> {
                    throw new RuntimeException("Tree " + tree.name + " don't hava " + name + "block");
                },
                defItem);
    }

    TreeBlockType(String name,
                  BiFunction<Tree, BlockBehaviour.Properties, Block> defBlock,
                  Function<Tree, BlockBehaviour.Properties> defBlockProp) {
        this(name, true, false, defBlock, defBlockProp,
                (tree, type) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't hava " + name + "item");
                });
    }

    TreeBlockType(String name) {
        this(name, false, false,
                (tree, v2) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't hava " + name + "block");
                },
                tree -> {
                    throw new RuntimeException("Tree " + tree.name + " don't hava " + name + "block");
                },
                (tree, v2) -> {
                    throw new RuntimeException("Tree " + tree.name + " don't hava " + name + "item");
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
