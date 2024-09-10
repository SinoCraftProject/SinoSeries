package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TestBlockStateProvider extends BlockStateProvider {

    public TestBlockStateProvider(IDataGenContext context) {
        super(context.getOutput(), context.getModId(), (ExistingFileHelper) context.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        cubeAll(TestRegistry.TEST_BLOCK.get());
        simpleBlock(TestRegistry.TEST_BLOCK.get());
    }
}
