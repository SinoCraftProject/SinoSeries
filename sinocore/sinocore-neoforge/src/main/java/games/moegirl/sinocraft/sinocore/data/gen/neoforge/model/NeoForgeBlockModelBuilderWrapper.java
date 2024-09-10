package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.model.IBlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;

public class NeoForgeBlockModelBuilderWrapper
        extends NeoForgeModelBuilderWrapper<BlockModelBuilder, NeoForgeBlockModelBuilderWrapper>
        implements IBlockModelBuilder<NeoForgeBlockModelBuilderWrapper> {

    public NeoForgeBlockModelBuilderWrapper(BlockModelBuilder modelBuilder) {
        super(modelBuilder);
    }
}
