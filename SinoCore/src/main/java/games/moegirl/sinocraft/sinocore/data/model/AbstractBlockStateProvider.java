package games.moegirl.sinocraft.sinocore.data.model;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * The default base class of BlockState data generator in SinoCore.
 *
 * @author qyl27
 */
public abstract class AbstractBlockStateProvider extends BlockStateProvider {

    protected final Logger logger;

    protected final String modid;

    protected final AbstractBlockModelProvider blockModels;

    protected final AbstractItemModelProvider itemModels;

    public AbstractBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.modid = modid;

        this.logger = LoggerFactory.getLogger(getName());

        this.blockModels = new AbstractBlockModelProvider(output, modid, exFileHelper) {
            @Override
            protected void registerModels() {
            }
        };

        this.itemModels = new AbstractItemModelProvider(output, modid, this.blockModels.existingFileHelper) {
            @Override
            protected void registerModels() {
            }
        };
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output)
                .thenRun(blockModels::printExceptions)
                .thenRun(itemModels::printExceptions);
    }

    protected abstract void registerStatesAndModels();

    public BlockModelProvider models() {
        return blockModels;
    }

    public ItemModelProvider itemModels() {
        return itemModels;
    }

    protected ResourceLocation blockLoc(ResourceLocation path) {
        return new ResourceLocation(path.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + path.getPath());
    }
}
