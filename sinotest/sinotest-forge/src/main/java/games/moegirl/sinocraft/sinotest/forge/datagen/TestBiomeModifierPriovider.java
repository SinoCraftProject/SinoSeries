package games.moegirl.sinocraft.sinotest.forge.datagen;

import games.moegirl.sinocraft.sinocore.datagen.AbstractBiomeModifierProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinotest.data.TestKeys;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class TestBiomeModifierPriovider extends AbstractBiomeModifierProvider {

    public TestBiomeModifierPriovider(IDataGenContext context) {
        super(context);
    }

    @Override
    protected void registerBiomeModifiers() {
        add(new Feature(TestKeys.TEST_FEATURE, GenerationStep.Decoration.UNDERGROUND_ORES, BiomeTags.IS_OVERWORLD));
    }
}
