package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractAutoItemModelProvider;
import games.moegirl.sinocraft.sinofoundation.SFDTrees;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SFDItemModelProvider extends AbstractAutoItemModelProvider {

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

        chest(SFDBlockItems.JUJUBE_CHEST, SFDBlockItems.JUJUBE_TRAPPED_CHEST, SFDTrees.JUJUBE);
    }
}
