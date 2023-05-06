package games.moegirl.sinocraft.sinodivination.old.data.provider;

import games.moegirl.sinocraft.sinocore.data.ItemModelProviderBase;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SDItemModelProvider extends ItemModelProviderBase {

    public SDItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), SinoDivination.MODID, event.getExistingFileHelper(), SDItems.REGISTRY);
    }

    @Override
    protected void registerItemModels() {
        chest(SDBlocks.COTINUS_CHEST);
        chest(SDBlocks.JUJUBE_CHEST);
        chest(SDBlocks.SOPHORA_CHEST);

        basicItem(SDItems.RICE.get());
        basicItem(SDItems.REHMANNIA.get());
        basicItem(SDItems.SESAME.get());
        basicItem(SDItems.DRAGONLIVER_MELON.get());
        basicItem(SDItems.WORMWOOD_LEAF.get());
        basicItem(SDItems.GARLIC.get());
    }

    protected <T extends WoodenChest> void chest(RegistryObject<T> chestObj) {
        T chest = chestObj.get();
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(chest.planks());
        ResourceLocation texPlank = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
        singleTexture(chestObj.getId().getPath(), mcLoc("item/chest"), "particle", texPlank);
    }
}
