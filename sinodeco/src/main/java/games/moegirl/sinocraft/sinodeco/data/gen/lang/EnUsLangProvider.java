package games.moegirl.sinocraft.sinodeco.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class EnUsLangProvider extends AbstractLanguageProvider {
    public EnUsLangProvider(DataGenContext context) {
        super(context, "en_us");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addBlock(SDBlocks.ACACIA_WOOD_TABLE.get(), "Acacia Table");
        delegate.addBlock(SDBlocks.BAMBOO_WOOD_TABLE.get(), "Bamboo Table");
        delegate.addBlock(SDBlocks.BIRCH_WOOD_TABLE.get(), "Birch Table");
        delegate.addBlock(SDBlocks.CHERRY_WOOD_TABLE.get(), "Cherry Table");
        delegate.addBlock(SDBlocks.CRIMSON_WOOD_TABLE.get(), "Crimson Table");
        delegate.addBlock(SDBlocks.DARK_OAK_WOOD_TABLE.get(), "Dark Oak Table");
        delegate.addBlock(SDBlocks.JUNGLE_WOOD_TABLE.get(), "Jungle Table");
        delegate.addBlock(SDBlocks.MANGROVE_WOOD_TABLE.get(), "Mangrove Table");
        delegate.addBlock(SDBlocks.OAK_WOOD_TABLE.get(), "Oak Table");
        delegate.addBlock(SDBlocks.SPRUCE_WOOD_TABLE.get(), "Spruce Table");
        delegate.addBlock(SDBlocks.WARPED_WOOD_TABLE.get(), "Warped Table");
        delegate.addBlock(SDBlocks.PEACH_WOOD_TABLE.get(), "Peach Table");

        delegate.addBlock(SDBlocks.MARBLE_BLOCK.get(), "Marble Block");
        delegate.addBlock(SDBlocks.CHISELED_MARBLE.get(), "Chiseled Marble Block");
        delegate.addBlock(SDBlocks.MARBLE_PILLAR.get(), "Marble Pillar");
        delegate.addBlock(SDBlocks.MARBLE_STAIRS.get(), "Marble Stairs");
        delegate.addBlock(SDBlocks.MARBLE_SLAB.get(), "Marble Slab");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE.get(), "Smooth Marble Block");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), "Smooth Marble Stairs");
        delegate.addBlock(SDBlocks.SMOOTH_MARBLE_SLAB.get(), "Smooth Marble Slab");
        delegate.addBlock(SDBlocks.MARBLE_BRICKS.get(), "Marble Bricks");
        delegate.addBlock(SDBlocks.MARBLE_BALUSTRADE.get(), "Marble Balustrade");

        delegate.addTab(SDItems.SINO_DECO_TAB, "SinoDeco");

        delegate.addBlock(SDBlocks.PEACH_LEAVES.get(), "Peach Leaves");
        delegate.addBlock(SDBlocks.PEACH_SAPLING.get(), "Peach Sapling");
        delegate.addBlock(SDBlocks.PEACH_LOG.get(), "Peach Log");
        delegate.addBlock(SDBlocks.PEACH_WOOD.get(), "Peach Wood");
        delegate.addBlock(SDBlocks.STRIPPED_PEACH_LOG.get(), "Stripped Peach Log");
        delegate.addBlock(SDBlocks.STRIPPED_PEACH_WOOD.get(), "Stripped Peach Wood");
        delegate.addBlock(SDBlocks.PEACH_PLANKS.get(), "Peach Planks");
        delegate.addBlock(SDBlocks.PEACH_STAIRS.get(), "Peach Stairs");
        delegate.addBlock(SDBlocks.PEACH_SLAB.get(), "Peach Slab");
        delegate.addBlock(SDBlocks.PEACH_FENCE.get(), "Peach Fence");
        delegate.addBlock(SDBlocks.PEACH_FENCE_GATE.get(), "Peach Fence Gate");
        delegate.addBlock(SDBlocks.PEACH_DOOR.get(), "Peach Door");
        delegate.addBlock(SDBlocks.PEACH_TRAPDOOR.get(), "Peach Trapdoor");
        delegate.addBlock(SDBlocks.PEACH_PRESSURE_PLATE.get(), "Peach Pressure Plate");
        delegate.addBlock(SDBlocks.PEACH_BUTTON.get(), "Peach Button");
        delegate.addBlock(SDBlocks.PEACH_SIGN.get(), "Peach Sign");
        delegate.addBlock(SDBlocks.PEACH_HANGING_SIGN.get(), "Peach Hanging Sign");
        delegate.addBlock(SDBlocks.PEACH_CHEST.get(), "Peach Chest");
        delegate.addItem(SDItems.PEACH_BOAT.get(), "Peach Boat");
        delegate.addItem(SDItems.PEACH_CHEST_BOAT.get(), "Peach Boat with Chest");
    }
}
