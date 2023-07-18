package games.moegirl.sinocraft.sinofeast.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.model.BaseAutoItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SFItemModelProvider extends BaseAutoItemModelProvider {
    @SafeVarargs
    public SFItemModelProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper, DeferredRegister<? extends Item>... registers) {
        super(output, modid, exFileHelper, registers);
    }

    @Override
    protected void registerItemModels() {

    }
}
