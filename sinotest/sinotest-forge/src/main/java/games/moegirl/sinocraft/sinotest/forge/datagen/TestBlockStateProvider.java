package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

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
