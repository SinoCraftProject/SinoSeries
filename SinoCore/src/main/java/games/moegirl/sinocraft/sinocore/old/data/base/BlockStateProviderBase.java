package games.moegirl.sinocraft.sinocore.old.data.base;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockStateProvider;
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
 * BlockStateProviderBase
 *
 * @author skyinr
 */
public class BlockStateProviderBase extends AbstractBlockStateProvider {
    private final DeferredRegister<? extends Block> deferredRegister;
    private boolean adding = true;
    private final Set<Block> skipBlocks = new HashSet<>();

    public BlockStateProviderBase(PackOutput output, String modId, ExistingFileHelper existingFileHelper, DeferredRegister<? extends Block> deferredRegister) {
        super(output, modId, existingFileHelper);
        this.deferredRegister = deferredRegister;
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> blocks = getBlocks();
        blocks.removeAll(skipBlocks);

        registerBlock(blocks);
    }

    protected Set<Block> getBlocks() {
        // skyinr: Register models and state for blocks
        return deferredRegister.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet());
    }

    protected void registerBlock(Set<Block> blocks) {
        blocks.forEach(this::simpleBlock);
    }

    protected void skipBlock(Block... blocks) {
        skipBlocks.addAll(Arrays.asList(blocks));
    }

    @Override
    public VariantBlockStateBuilder getVariantBuilder(Block b) {
        if (isAdding()) {
            skipBlock(b);
        }
        return super.getVariantBuilder(b);
    }

    @Override
    public MultiPartBlockStateBuilder getMultipartBuilder(Block b) {
        if (isAdding()) {
            skipBlock(b);
        }
        return super.getMultipartBuilder(b);
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }
}
