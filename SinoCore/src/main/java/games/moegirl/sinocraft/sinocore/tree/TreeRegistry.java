package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.tree.event.TreeGatherDataListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
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

    public static List<Tree> getTrees(String modid) {
        return TREE_REGISTRY.getOrDefault(modid, List.of());
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
        blockEntityRegister.register(bus);
        itemRegister.register(bus);

        bus.register(new TreeGatherDataListener(modid));
        for (Tree tree : getTrees(modid)) {
            tree.register(blockRegister, blockEntityRegister, itemRegister);
        }
    }
}
