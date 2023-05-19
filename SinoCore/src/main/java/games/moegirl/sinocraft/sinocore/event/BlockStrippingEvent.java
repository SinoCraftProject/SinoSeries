package games.moegirl.sinocraft.sinocore.event;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockStrippingEvent {
    private static final Map<Supplier<Block>, Tuple<Supplier<Block>, Supplier<ItemLike>>> BLOCK_STRIPPING_MAP = new HashMap<>();

    static {
        registerStripping(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD, Items.AIR);
        registerStripping(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Items.AIR);
        registerStripping(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD, Items.AIR);
        registerStripping(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Items.AIR);
        registerStripping(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD, Items.AIR);
        registerStripping(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Items.AIR);
        registerStripping(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD, Items.AIR);
        registerStripping(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Items.AIR);
        registerStripping(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Items.AIR);
        registerStripping(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Items.AIR);
        registerStripping(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD, Items.AIR);
        registerStripping(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Items.AIR);
        registerStripping(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD, Items.AIR);
        registerStripping(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Items.AIR);
        registerStripping(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, Items.AIR);
        registerStripping(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE, Items.AIR);
        registerStripping(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, Items.AIR);
        registerStripping(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE, Items.AIR);
        registerStripping(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD, Items.AIR);
        registerStripping(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Items.AIR);
        registerStripping(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK, Items.AIR);
    }

    public static void registerStripping(Supplier<Block> source, Supplier<Block> target, Supplier<ItemLike> item) {
        BLOCK_STRIPPING_MAP.put(source, new Tuple<>(target, item));
    }

    public static void registerStripping(Block source, Block target, ItemLike item) {
        BLOCK_STRIPPING_MAP.put(() -> source, new Tuple<>(() -> target, () -> item));
    }

    public static Map<Supplier<Block>, Tuple<Supplier<Block>, Supplier<ItemLike>>> getBlockStrippingMap() {
        return BLOCK_STRIPPING_MAP;
    }

    @SubscribeEvent
    public static void onBlockStripping(BlockEvent.BlockToolModificationEvent event) {
        // Todo: qyl27: stripping wood block. maybe from tree registry?
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            var context = event.getContext();
            var level = context.getLevel();
            var pos = context.getClickedPos();
            var state = level.getBlockState(pos);
            var player = event.getPlayer();

            if (!BLOCK_STRIPPING_MAP.containsKey(state.getBlock())) {
                return;
            }

            if (!event.isSimulated()) {
                var tuple = BLOCK_STRIPPING_MAP.get(state.getBlock());

                var finalState = tuple.getA().get().defaultBlockState();
                for (Property property : state.getProperties()) {   // Type gymnastics.
                    var pValue = state.getValue(property);
                    finalState = finalState.trySetValue(property, pValue);
                }
                event.setFinalState(finalState);

                if (tuple.getB() != Items.AIR) {
                    player.drop(new ItemStack(tuple.getB().get()), false);
                }
            }
        }
    }
}
