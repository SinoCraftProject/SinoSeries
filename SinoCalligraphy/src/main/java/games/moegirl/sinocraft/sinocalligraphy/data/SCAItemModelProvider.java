package games.moegirl.sinocraft.sinocalligraphy.data;

import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.ItemModelProviderBase;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SCAItemModelProvider extends ItemModelProviderBase {
    public SCAItemModelProvider(PackOutput output, String modId, ExistingFileHelper exHelper, DeferredRegister<? extends Item>... deferredRegisters) {
        super(output, modId, exHelper, deferredRegisters);
    }

    @Override
    protected void registerItemModels() {
        skipItem(SCAItems.FAN.get());
        skipItem(SCAItems.FAN_FOLDED.get());
        skipItem(SCAItems.BRUSH.get());
        skipItem(SCAItems.INK.get());
    }
}
