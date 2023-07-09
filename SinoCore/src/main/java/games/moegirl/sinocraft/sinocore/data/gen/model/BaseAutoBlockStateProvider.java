package games.moegirl.sinocraft.sinocore.data.gen.model;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@code BlockStateProviderBase} <br />
 * {@link AbstractBlockStateProvider} with deferred registers auto register.
 *
 * @author skyinr, qyl27
 */
public abstract class BaseAutoBlockStateProvider extends AbstractBlockStateProvider {
    private final DeferredRegister<? extends Block>[] deferredRegisters;
    private final Set<Block> skipBlocks = new HashSet<>();
    private final Set<Block> addedBlocks = new HashSet<>();
    private boolean adding = false;

    @SafeVarargs
    public BaseAutoBlockStateProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper, DeferredRegister<? extends Block>... deferredRegisters) {
        super(output, modId, existingFileHelper);

        this.deferredRegisters = deferredRegisters;
    }

    protected abstract void registerBlockStatesAndModels();

    @Override
    protected void registerStatesAndModels() {
        setAdding(true);
        registerBlockStatesAndModels();
        setAdding(false);

        getBlocks().forEach(this::simpleBlock);
    }

    protected Set<Block> getBlocks() {
        // skyinr: Register models and state for blocks
        return Arrays.stream(deferredRegisters)
                .flatMap(dr -> dr.getEntries().stream())
                .map(RegistryObject::get)
                .filter(b -> !skipBlocks.contains(b))
                .filter(b -> !addedBlocks.contains(b))
                .collect(Collectors.toSet());
    }

    @Override
    public VariantBlockStateBuilder getVariantBuilder(Block b) {
        if (isAdding()) {
            addedBlock(b);
        }
        return super.getVariantBuilder(b);
    }

    @Override
    public MultiPartBlockStateBuilder getMultipartBuilder(Block b) {
        if (isAdding()) {
            addedBlock(b);
        }
        return super.getMultipartBuilder(b);
    }

    protected boolean isAdding() {
        return adding;
    }

    private void setAdding(boolean adding) {
        this.adding = adding;
    }

    protected void skipBlock(Block... blocks) {
        skipBlocks.addAll(Arrays.asList(blocks));
    }

    private void addedBlock(Block... blocks) {
        addedBlocks.addAll(Arrays.asList(blocks));
    }
}
