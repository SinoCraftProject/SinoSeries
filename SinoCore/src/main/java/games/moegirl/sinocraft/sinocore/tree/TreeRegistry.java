package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.tree.event.SCTreeGatherDataListener;
import games.moegirl.sinocraft.sinocore.tree.event.SCTreeTabsBuildListener;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Supplier;

/**
 * Registry of Tree.
 * @author qyl27
 */
public final class TreeRegistry {

    private static final Map<String, List<Tree>> TREE_REGISTRY = new HashMap<>();
    private static final Map<String, Pair<DeferredRegister<Block>, DeferredRegister<Item>>> MOD_DEFERRED_REGISTERS = new HashMap<>();

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
     * @param bus Mod Event Bus {@code FMLModContainer#getModEventBus()}
     */
    public static void register(String modid, IEventBus bus) {
        registerListeners(modid, bus);

        for (var tree : getRegistry().get(modid)) {
            registerInternal(modid, tree);
        }
    }

    /**
     * Add a TreeType by TreeType.Builder.
     * @param tree TreeType
     */
    public static void addTree(Tree tree) {
        var modid = tree.getName().getNamespace();
        if (!getRegistry().containsKey(modid)) {
            getRegistry().put(modid, new ArrayList<>());
        }

        getRegistry().get(modid).add(tree);
    }



    private static void registerListeners(String modid, IEventBus bus) {
        var blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        var itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        MOD_DEFERRED_REGISTERS.put(modid, Pair.of(blockRegister, itemRegister));

        bus.register(new SCTreeTabsBuildListener(modid));
        bus.register(new SCTreeGatherDataListener(modid));

        blockRegister.register(bus);
        itemRegister.register(bus);
    }

    private static void registerInternal(String modid, Tree tree) {
        var pair = MOD_DEFERRED_REGISTERS.get(modid);
        tree.register(pair.getLeft(), pair.getRight());
    }
}
