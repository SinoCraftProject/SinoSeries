package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.ItemModelProviderBase;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SFDItemModelProvider extends ItemModelProviderBase {

    @SafeVarargs
    public SFDItemModelProvider(PackOutput output, String modId, ExistingFileHelper exHelper,
                                DeferredRegister<? extends ItemLike>... deferredRegisters) {
        super(output, modId, exHelper, deferredRegisters);
    }

    @Override
    protected void registerItemModels() {
        skipItem(SFDBlockItems.STOVE.get());
        skipItem(SFDBlockItems.WOOD_DESK.get());
        skipItem(SFDBlockItems.WOOD_CHAIRS.get());
        skipItem(SFDBlockItems.MARBLE_WALLS.get());

        getBuilder(SFDBlockItems.STOVE.getKey().location().getPath()).parent(weakCheckModel(modLoc("block/stove_off")));
        getBuilder(SFDBlockItems.WOOD_DESK.getKey().location().getPath()).parent(weakCheckModel(modLoc("block/wood_desk_no_connection")));
        getBuilder(SFDBlockItems.WOOD_CHAIRS.getKey().location().getPath()).parent(weakCheckModel(modLoc("block/wood_chairs")));
        getBuilder(SFDBlockItems.MARBLE_WALLS.getKey().location().getPath()).parent(weakCheckModel(modLoc("block/marble_wall_inventory")));

        chest(SFDBlockItems.COTINUS_CHEST, SFDBlockItems.COTINUS_TRAPPED_CHEST, SFDTrees.COTINUS);
        chest(SFDBlockItems.JUJUBE_CHEST, SFDBlockItems.JUJUBE_TRAPPED_CHEST, SFDTrees.JUJUBE);
        chest(SFDBlockItems.SOPHORA_CHEST, SFDBlockItems.SOPHORA_TRAPPED_CHEST, SFDTrees.SOPHORA);
    }

    private void chest(RegistryObject<? extends Item> chest, RegistryObject<? extends Item> trappedChest, Tree tree) {
        ResourceLocation name = tree.getBlockObj(TreeBlockType.PLANKS).getId();
        ResourceLocation texPlank = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
        singleTexture(chest.getId().getPath(), mcLoc("item/chest"), "particle", texPlank);
        singleTexture(trappedChest.getId().getPath(), mcLoc("item/chest"), "particle", texPlank);
    }
}
