package games.moegirl.sinocraft.sinocore.event;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockStrippingEvent {
    private static final Map<ResourceLocation, Triple<Supplier<Block>, Supplier<Block>, Supplier<? extends ItemLike>>> DEFERRED_BLOCK_STRIPPING_MAP = new HashMap<>();

    static {
        registerStripping(mcLoc("oak_wood"), Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD, Items.AIR);
        registerStripping(mcLoc("oak_log"), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Items.AIR);
        registerStripping(mcLoc("dark_oak_wood"), Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD, Items.AIR);
        registerStripping(mcLoc("dark_oak_log"), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Items.AIR);
        registerStripping(mcLoc("acacia_wood"), Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD, Items.AIR);
        registerStripping(mcLoc("acacia_log"), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Items.AIR);
        registerStripping(mcLoc("cherry_wood"), Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD, Items.AIR);
        registerStripping(mcLoc("cherry_log"), Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Items.AIR);
        registerStripping(mcLoc("birch_wood"), Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Items.AIR);
        registerStripping(mcLoc("birch_log"), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Items.AIR);
        registerStripping(mcLoc("jungle_wood"), Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD, Items.AIR);
        registerStripping(mcLoc("jungle_log"), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Items.AIR);
        registerStripping(mcLoc("spruce_wood"), Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD, Items.AIR);
        registerStripping(mcLoc("spruce_log"), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Items.AIR);
        registerStripping(mcLoc("warped_wood"), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, Items.AIR);
        registerStripping(mcLoc("warped_log"), Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE, Items.AIR);
        registerStripping(mcLoc("crimson_wood"), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, Items.AIR);
        registerStripping(mcLoc("crimson_log"), Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE, Items.AIR);
        registerStripping(mcLoc("mangrove_wood"), Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD, Items.AIR);
        registerStripping(mcLoc("mangrove_log"), Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Items.AIR);
        registerStripping(mcLoc("bamboo_block"), Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK, Items.AIR);
    }

    private static ResourceLocation mcLoc(String path) {
        return new ResourceLocation("minecraft", path);
    }

    public static void registerStripping(ResourceLocation rl, Supplier<Block> source, Supplier<Block> target, Supplier<? extends ItemLike> item) {
        DIRTY_FLAG = true;
        DEFERRED_BLOCK_STRIPPING_MAP.put(rl, new ImmutableTriple<>(source, target, item));
    }

    public static void registerStripping(ResourceLocation rl, Block source, Block target, ItemLike item) {
        registerStripping(rl, () -> source, () -> target, () -> item);
    }

    public static void registerStripping(Tree tree, Supplier<? extends ItemLike> item) {
        registerStripping(tree.name, () -> tree.getBlock(TreeBlockType.LOG), () -> tree.getBlock(TreeBlockType.STRIPPED_LOG), item);
        registerStripping(tree.name, () -> tree.getBlock(TreeBlockType.LOG_WOOD), () -> tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD), item);
    }

    public static Map<ResourceLocation, Triple<Supplier<Block>, Supplier<Block>, Supplier<? extends ItemLike>>> getDeferredBlockStrippingMap() {
        return ImmutableMap.copyOf(DEFERRED_BLOCK_STRIPPING_MAP);
    }

    private static Map<Block, Tuple<Block, ItemLike>> getBlockStrippingMap() {
        var map = new HashMap<Block, Tuple<Block, ItemLike>>();
        for (var entry : DEFERRED_BLOCK_STRIPPING_MAP.entrySet()) {
            map.put(entry.getValue().getLeft().get(), new Tuple<>(entry.getValue().getMiddle().get(), entry.getValue().getRight().get()));
        }

        return ImmutableMap.copyOf(map);
    }

    private static final List<Supplier<? extends ItemLike>> DEFERRED_TOOL_LIST = new ArrayList<>();

    public static void registerTool(Supplier<? extends ItemLike> tool) {
        DEFERRED_TOOL_LIST.add(tool);
    }

    private static Map<Block, Tuple<Block, ItemLike>> FROZE_STRIPPING_MAP;
    private static boolean DIRTY_FLAG = true;

    private static Map<Block, Tuple<Block, ItemLike>> getFrozenMap() {
        if (DIRTY_FLAG || FROZE_STRIPPING_MAP == null) {
            FROZE_STRIPPING_MAP = getBlockStrippingMap();
            DIRTY_FLAG = false;
        }
        return FROZE_STRIPPING_MAP;
    }

    private static List<ItemLike> FROZE_TOOL_LIST;

    private static List<ItemLike> getToolList() {
        var list = new ArrayList<ItemLike>();
        for (var i : DEFERRED_TOOL_LIST) {
            list.add(i.get());
        }

        return ImmutableList.copyOf(list);
    }

    private static List<ItemLike> getFrozenToolList() {
        if (DIRTY_FLAG || FROZE_TOOL_LIST == null) {
            FROZE_TOOL_LIST = getToolList();
            DIRTY_FLAG = false;
        }
        return FROZE_TOOL_LIST;
    }

    @SubscribeEvent
    public static void onBlockStripping(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            var context = event.getContext();
            var level = context.getLevel();
            var pos = context.getClickedPos();
            var state = level.getBlockState(pos);
            var player = event.getPlayer();
            var tool = player.getItemInHand(context.getHand());

            if (!getFrozenMap().containsKey(state.getBlock())) {
                return;
            }

            if (!event.isSimulated()) {
                var tuple = getFrozenMap().get(state.getBlock());

                var finalState = tuple.getA().defaultBlockState();
                for (Property property : state.getProperties()) {   // Type gymnastics.
                    var pValue = state.getValue(property);
                    finalState = finalState.trySetValue(property, pValue);
                }
                event.setFinalState(finalState);

                if (tuple.getB() != Items.AIR && getFrozenToolList().contains(tool.getItem())) {
                    player.drop(new ItemStack(tuple.getB()), false);
                }
            }
        }
    }
}
