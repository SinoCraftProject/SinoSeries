package games.moegirl.sinocraft.sinocore.old.data.base;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public abstract class WarnBlockStateProvider extends BlockStateProvider {

    private final String modid;
    private final WarnBlockModelProvider blockModels;
    private final WarnItemModelProvider itemModels;

    public WarnBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.modid = modid;
        this.blockModels = new WarnBlockModelProvider(output, modid, exFileHelper) {
            @Override
            protected void registerModels() {
            }
        };
        this.itemModels = new WarnItemModelProvider(output, modid, this.blockModels.existingFileHelper) {
            @Override
            protected void registerModels() {
            }
        };
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output)
                .thenRun(blockModels::printAllExceptions)
                .thenRun(itemModels::printAllExceptions);
    }

    protected abstract void registerStatesAndModels();

    public BlockModelProvider models() {
        return blockModels;
    }

    public ItemModelProvider itemModels() {
        return itemModels;
    }

    @Nonnull
    @Override
    public String getName() {
        return "BlockStates: " + modid;
    }
}
