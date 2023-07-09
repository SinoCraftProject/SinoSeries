package games.moegirl.sinocraft.sinodivination.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.tag.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDItemTags;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraftforge.common.Tags;
import net.minecraftforge.data.event.GatherDataEvent;

class ProviderTagItem extends AbstractItemTagsProvider {

    public ProviderTagItem(GatherDataEvent event, ProviderTagBlock provider) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), provider.contentsGetter(), SinoDivination.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addToTags() {
        tag(SDTags.JADE_SACRIFICIAL_UTENSIL).add(SDItems.CANG_BI.get(), SDItems.HUANG_CONG.get(), SDItems.QING_GUI.get(), SDItems.CHI_ZHANG.get(), SDItems.BAI_HU.get(), SDItems.XUAN_HUANG.get());
        tag(SDTags.COPPER_SACRIFICIAL_UTENSIL).add(SDItems.COPPER_GOBLET.get(), SDItems.COPPER_DAGGER_AXE.get(), SDItems.COPPER_MIRROR.get(), SDItems.COPPER_MASK.get(), SDItems.COPPER_LAMP.get(), SDItems.COPPER_BEAST.get());
        tag(SDTags.SACRIFICIAL_UTENSIL_MATERIAL).add(SFDItems.JADE.get()).addTag(Tags.Items.INGOTS_COPPER);
        tag(SDTags.SACRIFICIAL_UTENSIL).addTag(SDTags.COPPER_SACRIFICIAL_UTENSIL).addTag(SDTags.JADE_SACRIFICIAL_UTENSIL);
        tag(SFDItemTags.STACK_SOPHORA).add(SDItems.STICK_SOPHORA.get());
    }
}
