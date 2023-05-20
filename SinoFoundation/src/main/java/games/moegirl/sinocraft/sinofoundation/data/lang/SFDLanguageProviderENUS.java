package games.moegirl.sinocraft.sinofoundation.data.lang;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
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

        add(SFDItems.IRON_KNIFE.get(), "Iron Knife");
        add(SFDItems.GOLD_KNIFE.get(), "Golden Knife");
        add(SFDItems.DIAMOND_KNIFE.get(), "Diamond Knife");
        add(SFDItems.ASHES.get(), "Ashes");
        add(SFDItems.TREE_BARK.get(), "Tree Bark");

        add(SFDBlocks.STOVE.get(), "Stove");

        add(SFDConstants.TRANSLATE_STOVE_FULL_OF_ASHES, "Stove is full of ashes. With empty hand, Right click to pick up ashes; Shift + Right click to remove all the ashes.");
        add(SFDConstants.TRANSLATE_STOVE_NOT_A_FUEL, "The item in your hand is not a valid fuel.");
        add(SFDConstants.TRANSLATE_STOVE_EMPTY_NOW, "Stove is empty now.");
    }
}
