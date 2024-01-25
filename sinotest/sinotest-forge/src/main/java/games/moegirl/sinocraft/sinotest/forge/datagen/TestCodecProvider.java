package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractCodecProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.data.TestKeys;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;

public class TestCodecProvider extends AbstractCodecProvider {

    public TestCodecProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void generateData() {
        add(TestKeys.TEST_FEATURE.location(), new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new RandomBlockMatchTest(Blocks.GRASS_BLOCK, 1),
                        TestRegistry.TEST_BLOCK.get().defaultBlockState(), 50, 1)));
    }
}
