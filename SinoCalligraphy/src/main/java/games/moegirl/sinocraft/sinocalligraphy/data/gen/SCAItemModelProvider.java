package games.moegirl.sinocraft.sinocalligraphy.data.gen;

import games.moegirl.sinocraft.sinocalligraphy.block.SCABlocks;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractAutoItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SCAItemModelProvider extends AbstractAutoItemModelProvider {
    public SCAItemModelProvider(PackOutput output, String modId, ExistingFileHelper exHelper, DeferredRegister<? extends Item>... deferredRegisters) {
        super(output, modId, exHelper, deferredRegisters);
    }

    @Override
    protected void registerItemModels() {
        skipItem(SCAItems.FAN.get());
        skipItem(SCAItems.FAN_FOLDED.get());
        skipItem(SCAItems.BRUSH.get());
        skipItem(SCAItems.INK.get());
        skipItem(SCAItems.WOOD_PULP_BUCKET.get());

        blockItem(SCABlocks.PAPER_DRYING_RACK.get(), "0");
    }
}

