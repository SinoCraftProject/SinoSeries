package games.moegirl.sinocraft.sinodeco.block.item;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class SDBlockItems {
    public static final IRegistry<Item> ITEMS = RegistryManager.obtain(SinoDeco.MODID, Registries.ITEM);

    public static void register() {
        ITEMS.register();
    }

    public static final Supplier<Item> ACACIA_WOOD_TABLE = ITEMS.register("acacia_wood_table", () -> new BlockItem(SDBlocks.ACACIA_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> BAMBOO_WOOD_TABLE = ITEMS.register("bamboo_wood_table", () -> new BlockItem(SDBlocks.BAMBOO_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> BIRCH_WOOD_TABLE = ITEMS.register("birch_wood_table", () -> new BlockItem(SDBlocks.BIRCH_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> CHERRY_WOOD_TABLE = ITEMS.register("cherry_wood_table", () -> new BlockItem(SDBlocks.CHERRY_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> CRIMSON_WOOD_TABLE = ITEMS.register("crimson_wood_table", () -> new BlockItem(SDBlocks.CRIMSON_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> DARK_OAK_WOOD_TABLE = ITEMS.register("dark_oak_wood_table", () -> new BlockItem(SDBlocks.DARK_OAK_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> JUNGLE_WOOD_TABLE = ITEMS.register("jungle_wood_table", () -> new BlockItem(SDBlocks.JUNGLE_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> MANGROVE_WOOD_TABLE = ITEMS.register("mangrove_wood_table", () -> new BlockItem(SDBlocks.MANGROVE_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> OAK_WOOD_TABLE = ITEMS.register("oak_wood_table", () -> new BlockItem(SDBlocks.OAK_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> SPRUCE_WOOD_TABLE = ITEMS.register("spruce_wood_table", () -> new BlockItem(SDBlocks.SPRUCE_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> WARPED_WOOD_TABLE = ITEMS.register("warped_wood_table", () -> new BlockItem(SDBlocks.WARPED_WOOD_TABLE.get(), decoTab()));
    public static final Supplier<Item> PEACH_WOOD_TABLE = ITEMS.register("peach_wood_table", () -> new BlockItem(SDBlocks.PEACH_WOOD_TABLE.get(), decoTab()));

    public static final IRegRef<Item> MARBLE_BLOCK = ITEMS.register("marble_block", () -> new BlockItem(SDBlocks.MARBLE_BLOCK.get(), decoTab()));
    public static final IRegRef<Item> CHISELED_MARBLE = ITEMS.register("chiseled_marble", () -> new BlockItem(SDBlocks.CHISELED_MARBLE.get(), decoTab()));
    public static final IRegRef<Item> MARBLE_PILLAR = ITEMS.register("marble_pillar", () -> new BlockItem(SDBlocks.MARBLE_PILLAR.get(), decoTab()));
    public static final IRegRef<Item> MARBLE_STAIRS = ITEMS.register("marble_stairs", () -> new BlockItem(SDBlocks.MARBLE_STAIRS.get(), decoTab()));
    public static final IRegRef<Item> MARBLE_SLAB = ITEMS.register("marble_slab", () -> new BlockItem(SDBlocks.MARBLE_SLAB.get(), decoTab()));
    public static final IRegRef<Item> SMOOTH_MARBLE = ITEMS.register("smooth_marble", () -> new BlockItem(SDBlocks.SMOOTH_MARBLE.get(), decoTab()));
    public static final IRegRef<Item> SMOOTH_MARBLE_STAIRS = ITEMS.register("smooth_marble_stairs", () -> new BlockItem(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), decoTab()));
    public static final IRegRef<Item> SMOOTH_MARBLE_SLAB = ITEMS.register("smooth_marble_slab", () -> new BlockItem(SDBlocks.SMOOTH_MARBLE_SLAB.get(), decoTab()));
    public static final IRegRef<Item> MARBLE_BRICKS = ITEMS.register("marble_bricks", () -> new BlockItem(SDBlocks.MARBLE_BRICKS.get(), decoTab()));
    public static final IRegRef<Item> MARBLE_BALUSTRADE = ITEMS.register("marble_balustrade", () -> new BlockItem(SDBlocks.MARBLE_BALUSTRADE.get(), decoTab()));

    public static final IRegRef<Item> PEACH_LEAVES = ITEMS.register("peach_leaves", () -> new BlockItem(SDBlocks.PEACH_LEAVES.get(), decoTab()));
    public static final IRegRef<Item> PEACH_SAPLING = ITEMS.register("peach_sapling", () -> new BlockItem(SDBlocks.PEACH_SAPLING.get(), decoTab()));
    public static final IRegRef<Item> PEACH_LOG = ITEMS.register("peach_log", () -> new BlockItem(SDBlocks.PEACH_LOG.get(), decoTab()));
    public static final IRegRef<Item> PEACH_WOOD = ITEMS.register("peach_wood", () -> new BlockItem(SDBlocks.PEACH_WOOD.get(), decoTab()));
    public static final IRegRef<Item> STRIPPED_PEACH_LOG = ITEMS.register("stripped_peach_log", () -> new BlockItem(SDBlocks.STRIPPED_PEACH_LOG.get(), decoTab()));
    public static final IRegRef<Item> STRIPPED_PEACH_WOOD = ITEMS.register("stripped_peach_wood", () -> new BlockItem(SDBlocks.STRIPPED_PEACH_WOOD.get(), decoTab()));
    public static final IRegRef<Item> PEACH_PLANKS = ITEMS.register("peach_planks", () -> new BlockItem(SDBlocks.PEACH_PLANKS.get(), decoTab()));
    public static final IRegRef<Item> PEACH_STAIRS = ITEMS.register("peach_stairs", () -> new BlockItem(SDBlocks.PEACH_STAIRS.get(), decoTab()));
    public static final IRegRef<Item> PEACH_SLAB = ITEMS.register("peach_slab", () -> new BlockItem(SDBlocks.PEACH_SLAB.get(), decoTab()));
    public static final IRegRef<Item> PEACH_FENCE = ITEMS.register("peach_fence", () -> new BlockItem(SDBlocks.PEACH_FENCE.get(), decoTab()));
    public static final IRegRef<Item> PEACH_FENCE_GATE = ITEMS.register("peach_fence_gate", () -> new BlockItem(SDBlocks.PEACH_FENCE_GATE.get(), decoTab()));
    public static final IRegRef<Item> PEACH_DOOR = ITEMS.register("peach_door", () -> new BlockItem(SDBlocks.PEACH_DOOR.get(), decoTab()));
    public static final IRegRef<Item> PEACH_TRAPDOOR = ITEMS.register("peach_trapdoor", () -> new BlockItem(SDBlocks.PEACH_TRAPDOOR.get(), decoTab()));
    public static final IRegRef<Item> PEACH_PRESSURE_PLATE = ITEMS.register("peach_pressure_plate", () -> new BlockItem(SDBlocks.PEACH_PRESSURE_PLATE.get(), decoTab()));
    public static final IRegRef<Item> PEACH_BUTTON = ITEMS.register("peach_button", () -> new BlockItem(SDBlocks.PEACH_BUTTON.get(), decoTab()));
    public static final IRegRef<Item> PEACH_SIGN = ITEMS.register("peach_sign", () -> new BlockItem(SDBlocks.PEACH_SIGN.get(), decoTab()));
    public static final IRegRef<Item> PEACH_HANGING_SIGN = ITEMS.register("peach_hanging_sign", () -> new BlockItem(SDBlocks.PEACH_HANGING_SIGN.get(), decoTab()));
    public static final IRegRef<Item> PEACH_CHEST = ITEMS.register("peach_chest", () -> new BlockItem(SDBlocks.PEACH_CHEST.get(), decoTab()));

    private static Item.Properties decoTab() {
        return new Item.Properties().sino$tab(SDItems.SINO_DECO_TAB);
    }
}
