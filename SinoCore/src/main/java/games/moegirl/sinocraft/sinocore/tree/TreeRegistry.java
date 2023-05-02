package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.tree.event.SCTreeGatherDataListener;
import games.moegirl.sinocraft.sinocore.tree.event.SCTreeTabsBuildListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of Tree.
 * @author qyl27
 */
public final class TreeRegistry {

    private static final Map<String, List<Tree>> TREE_REGISTRY = new HashMap<>();

    /**
     * Get tree registry.
     * @return Map&lt;String modid, List&lt;TreeType&gt; treeTypes&gt;
     */
    public static Map<String, List<Tree>> getRegistry() {
        return TREE_REGISTRY;
    }

    /**
     * Register mod trees.
     * @param modid ModId
     */
    public static void register(String modid) {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        var blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        var itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        blockRegister.register(bus);
        itemRegister.register(bus);
        register(modid, blockRegister, itemRegister);
    }

    public static void register(String modid, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SCTreeTabsBuildListener tabsListener = new SCTreeTabsBuildListener();
        bus.register(tabsListener);
        bus.register(new SCTreeGatherDataListener(modid));

        for (var tree : getRegistry().get(modid)) {
            tree.register(blockRegister, itemRegister, tabsListener);
        }
    }
}
