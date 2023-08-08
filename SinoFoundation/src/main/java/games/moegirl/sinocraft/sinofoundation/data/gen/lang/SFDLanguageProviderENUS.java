package games.moegirl.sinocraft.sinofoundation.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofoundation.SFDConstants;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.data.PackOutput;

public class SFDLanguageProviderENUS extends AbstractLanguageProvider {
    public SFDLanguageProviderENUS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addTab(SinoSeriesTabs.BUILDING_BLOCKS, "SinoSeries | Building Blocks");
        addTab(SinoSeriesTabs.FUNCTIONAL_BLOCKS, "SinoSeries | Functional Blocks");
        addTab(SinoSeriesTabs.AGRICULTURE, "SinoSeries | Agriculture");
        addTab(SinoSeriesTabs.TOOLS, "SinoSeries | Tools & Utilities");
        addTab(SinoSeriesTabs.WEAPONS, "SinoSeries | Combat");
        addTab(SinoSeriesTabs.MATERIALS, "SinoSeries | Ingredients");
        addTab(SinoSeriesTabs.MISC, "SinoSeries | Misc");

        addItem(SFDItems.IRON_KNIFE, "Iron Knife");
        addItem(SFDItems.GOLD_KNIFE, "Golden Knife");
        addItem(SFDItems.DIAMOND_KNIFE, "Diamond Knife");
        addItem(SFDItems.ASHES, "Ashes");
        addItem(SFDItems.TREE_BARK, "Tree Bark");
        addItem(SFDItems.MILLET, "Millet");
        addItem(SFDItems.CHILI_PEPPER, "Chili Pepper");
        addItem(SFDItems.GREEN_PEPPER, "Green Pepper");
        addItem(SFDItems.CABBAGE, "Cabbage");
        addItem(SFDItems.EGGPLANT, "Eggplant");
        addItem(SFDItems.BLACK_JADE, "Black Jade");
        addItem(SFDItems.GREEN_JADE, "Green Jade");
        addItem(SFDItems.RED_JADE, "Red Jade");
        addItem(SFDItems.WHITE_JADE, "White Jade");
        addItem(SFDItems.YELLOW_JADE, "Yellow Jade");
        addItem(SFDItems.NITER, "Niter");
        addItem(SFDItems.SULPHUR, "Sulphur");
        addItem(SFDItems.JUJUBE, "Jujube");
        addItem(SFDItems.SEED_WORMWOOD, "Argy Wormwood Seed");
        addItem(SFDItems.WORMWOOD_LEAF, "Argy Wormwood Leaves");
        addItem(SFDItems.RICE, "Rice");
        addItem(SFDItems.SEED_RICE, "Glutinous Rice Seed");
        addItem(SFDItems.SESAME, "Sesame");
        addItem(SFDItems.SEED_SESAME, "Jusheng Seed");

        addBlock(SFDBlocks.STOVE, "Stove");
        addBlock(SFDBlocks.WOOD_DESK, "Wood Desk");
        addBlock(SFDBlocks.WOOD_CHAIRS, "Wood Chairs");
        addBlock(SFDBlocks.WHITE_RADISH_PLANT, "White Radish");
        addBlock(SFDBlocks.SUMMER_RADISH_PLANT, "Summer Radish");
        addBlock(SFDBlocks.GREEN_RADISH_PLANT, "Green Radish");
        addBlock(SFDBlocks.CHILI_PEPPER_PLANT, "Chili Pepper");
        addBlock(SFDBlocks.GREEN_PEPPER_PLANT, "Green Pepper");
        addBlock(SFDBlocks.EGGPLANT_PLANT, "Eggplant");
        addBlock(SFDBlocks.CABBAGE_PLANT, "Cabbage");
        addBlock(SFDBlocks.MILLET_PLANT, "Millet");
        addBlock(SFDBlocks.SOYBEAN_PLANT, "Soybean");
        addBlock(SFDBlocks.GARLIC_PLANT, "Garlic");
        addBlock(SFDBlocks.MARBLE_BLOCK, "Marble Block");
        addBlock(SFDBlocks.SMOOTH_MARBLE, "Smooth Marble");
        addBlock(SFDBlocks.MARBLE_PILLAR, "Marble Pillar");
        addBlock(SFDBlocks.CHISELED_MARBLE_BLOCK, "Chiseled Marble Block");
        addBlock(SFDBlocks.MARBLE_SLAB, "Marble Slab");
        addBlock(SFDBlocks.MARBLE_STAIRS, "Marble Stairs");
        addBlock(SFDBlocks.MARBLE_WALLS, "Marble Walls");
        addBlock(SFDBlocks.SMOOTH_MARBLE_SLAB, "Smooth Marble Slab");
        addBlock(SFDBlocks.SMOOTH_MARBLE_STAIRS, "Smooth Marble Stairs");
        addBlock(SFDBlocks.BLACK_JADE_ORE, "Black Jade Ore");
        addBlock(SFDBlocks.DEEPSLATE_BLACK_JADE_ORE, "Deepslate Black Jade Ore");
        addBlock(SFDBlocks.GREEN_JADE_ORE, "Green Jade Ore");
        addBlock(SFDBlocks.DEEPSLATE_GREEN_JADE_ORE, "Deepslate Green Jade Ore");
        addBlock(SFDBlocks.RED_JADE_ORE, "Red Jade Ore");
        addBlock(SFDBlocks.DEEPSLATE_RED_JADE_ORE, "Deepslate Red Jade Ore");
        addBlock(SFDBlocks.WHITE_JADE_ORE, "White Jade Ore");
        addBlock(SFDBlocks.DEEPSLATE_WHITE_JADE_ORE, "Deepslate White Jade Ore");
        addBlock(SFDBlocks.YELLOW_JADE_ORE, "Yellow Jade Ore");
        addBlock(SFDBlocks.DEEPSLATE_YELLOW_JADE_ORE, "Deepslate Yellow Jade Ore");
        addBlock(SFDBlocks.NITER_ORE, "Niter Ore");
        addBlock(SFDBlocks.SULPHUR_ORE, "Sulphur Ore");
        addBlock(SFDBlocks.NETHER_SULPHUR_ORE, "Nether Sulphur Ore");
        addBlock(SFDBlocks.DEEPSLATE_SULPHUR_ORE, "Deepslate Sulphur Ore");
        addBlock(SFDBlocks.JUJUBE_CHEST, "Jujube Chest");
        addBlock(SFDBlocks.JUJUBE_TRAPPED_CHEST, "Jujube Trapped Chest");
        addBlock(SFDBlocks.WORMWOOD, "Wormwood");
        addBlock(SFDBlocks.RICE, "Glutinous Rise");
        addBlock(SFDBlocks.SESAME, "Jusheng");
        addBlock(SFDBlocks.LANTERN, "Lantern");
        addBlock(SFDBlocks.STONE_LANTERN, "Stone Lantern");

        addItem(SFDBlockItems.CHILI_PEPPER_SEED, "Chili Pepper Seed");
        addItem(SFDBlockItems.GREEN_PEPPER_SEED, "Green Pepper Seed");
        addItem(SFDBlockItems.EGGPLANT_SEED, "Eggplant Seed");
        addItem(SFDBlockItems.CABBAGE_SEED, "Cabbage Seed");
        addItem(SFDBlockItems.MILLET_SEED, "Millet Seed");

        add(SFDConstants.TRANSLATE_STOVE_FULL_OF_ASHES, "Stove is full of ashes. With empty hand, Right click to pick up ashes; Shift + Right click to remove all the ashes.");
        add(SFDConstants.TRANSLATE_STOVE_NOT_A_FUEL, "The item in your hand is not a valid fuel.");
        add(SFDConstants.TRANSLATE_STOVE_EMPTY_NOW, "Stove is empty now.");
    }
}
