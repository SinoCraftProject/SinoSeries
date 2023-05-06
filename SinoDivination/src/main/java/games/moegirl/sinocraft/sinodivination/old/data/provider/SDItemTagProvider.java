package games.moegirl.sinocraft.sinodivination.old.data.provider;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.old.data.SDTags;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraftforge.common.Tags;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class SDItemTagProvider extends AbstractItemTagsProvider {

    public SDItemTagProvider(GatherDataEvent event, SDBlockTagProvider provider) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), provider.contentsGetter(), SinoDivination.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addToTags() {
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);
        tag(SDTags.JADE_SACRIFICIAL_UTENSIL).add(SDItems.CANG_BI.get(), SDItems.HUANG_CONG.get(), SDItems.QING_GUI.get(), SDItems.CHI_ZHANG.get(), SDItems.BAI_HU.get(), SDItems.XUAN_HUANG.get());
        tag(SDTags.COPPER_SACRIFICIAL_UTENSIL).add(SDItems.COPPER_GOBLET.get(), SDItems.COPPER_DAGGER_AXE.get(), SDItems.COPPER_MIRROR.get(), SDItems.COPPER_MASK.get(), SDItems.COPPER_LAMP.get(), SDItems.COPPER_BEAST.get());
        tag(SDTags.SACRIFICIAL_UTENSIL_MATERIAL).add(SDItems.JADE.get()).addTag(Tags.Items.INGOTS_COPPER);
        tag(SDTags.SACRIFICIAL_UTENSIL).addTag(SDTags.COPPER_SACRIFICIAL_UTENSIL).addTag(SDTags.JADE_SACRIFICIAL_UTENSIL);

        tag(SDTags.FORGE_DUSTS_SULFUR).add(SDItems.SULPHUR.get());
        tag(SDTags.FORGE_DUSTS_NITER).add(SDItems.NITER.get());
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        tag(Tags.Items.CHESTS_WOODEN).add(chest.asItem());
        if (chest.isTrapped) {
            tag(Tags.Items.CHESTS_TRAPPED).add(chest.asItem());
        }
    }
}
