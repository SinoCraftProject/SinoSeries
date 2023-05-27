package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.tree.event.TreeClientEventHandler;
import games.moegirl.sinocraft.sinocore.tree.event.TreeGatherDataListener;
import games.moegirl.sinocraft.sinocore.tree.event.TreeTabsBuildListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of Tree.
 *
 * @author qyl27
 */
public final class TreeRegistry {

    private static final Map<String, List<Tree>> TREE_REGISTRY = Collections.synchronizedMap(new HashMap<>());

    /**
     * Get tree registry.
     *
     * @return Map&lt;String modid, List&lt;TreeType&gt; treeTypes&gt;
     */
    public static Map<String, List<Tree>> getRegistry() {
        return TREE_REGISTRY;
    }

    /**
     * Register mod trees.
     *
     * @param modid ModId
     */
    public static void register(String modid, IEventBus bus) {
        var blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        var blockEntityRegister = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modid);
        var itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        blockRegister.register(bus);
        itemRegister.register(bus);
        register(modid, bus, blockRegister, blockEntityRegister, itemRegister);
    }

    public static void register(String modid, IEventBus bus, DeferredRegister<Block> blockRegister,
                                DeferredRegister<BlockEntityType<?>> blockEntityRegister, DeferredRegister<Item> itemRegister) {
        TreeTabsBuildListener tabsListener = new TreeTabsBuildListener();
        bus.register(tabsListener);
        bus.register(new TreeGatherDataListener(modid));

        if (FMLEnvironment.dist.isClient()) {
            bus.register(new TreeClientEventHandler());
        }

        for (var tree : getRegistry().get(modid)) {
            tree.register(blockRegister, blockEntityRegister, itemRegister, tabsListener);
        }
    }
}
