package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.data.gen.ForgeProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.data.DataProvider;

import java.util.List;

public class TestForgeProviders implements ForgeProvider.IForgeProviders {

    @Override
    public String getModId() {
        return SinoTest.MODID;
    }

    @Override
    public List<DataProvider> allProviders(DataGenContext context) {
        return List.of(new TestBlockStateProvider(context));
    }
}
