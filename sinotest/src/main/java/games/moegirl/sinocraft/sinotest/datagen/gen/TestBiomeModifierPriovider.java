package games.moegirl.sinocraft.sinotest.datagen.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractBiomeModifierProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinotest.datagen.TestKeys;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class TestBiomeModifierPriovider extends AbstractBiomeModifierProvider {

    public TestBiomeModifierPriovider(DataGenContext context) {
        super(context);
    }

    @Override
    protected void registerBiomeModifiers() {
        add(new Feature(TestKeys.TEST_FEATURE, GenerationStep.Decoration.UNDERGROUND_ORES, BiomeTags.IS_OVERWORLD));
    }
}
