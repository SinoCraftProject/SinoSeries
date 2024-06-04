package games.moegirl.sinocraft.sinodeco.data.gen.forge;

import games.moegirl.sinocraft.sinocore.data.gen.ForgeProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SDForgeProvider implements ForgeProvider.IForgeProviders {
    @Override
    public @NotNull String getModId() {
        return SinoDeco.MODID;
    }

    @Override
    public @NotNull List<DataProvider> allProviders(IDataGenContext context) {
        return List.of(
                new ModBlockStateProvider(context.getOutput(), context.getModId(), (ExistingFileHelper) context.getExistingFileHelper())
        );
    }
}
