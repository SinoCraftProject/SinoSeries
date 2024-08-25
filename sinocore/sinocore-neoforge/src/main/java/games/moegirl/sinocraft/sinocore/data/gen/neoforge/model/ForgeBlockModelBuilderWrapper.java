package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.model.IBlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

public class ForgeBlockModelBuilderWrapper
        extends ForgeModelBuilderWrapper<BlockModelBuilder, ForgeBlockModelBuilderWrapper>
        implements IBlockModelBuilder<ForgeBlockModelBuilderWrapper> {

    public ForgeBlockModelBuilderWrapper(BlockModelBuilder modelBuilder) {
        super(modelBuilder);
    }
}
