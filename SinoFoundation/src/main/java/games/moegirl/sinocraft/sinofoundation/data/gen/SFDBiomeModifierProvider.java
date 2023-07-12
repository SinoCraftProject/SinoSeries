package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractBiomeModifierProvider;
import games.moegirl.sinocraft.sinofoundation.world.SFDFeatures;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * 用于生成 BiomeModifier，应当在所有 {@link net.minecraft.data.DataProvider} 之后调用
 *
 * @author luqin2007
 */
public class SFDBiomeModifierProvider extends AbstractBiomeModifierProvider {
    public SFDBiomeModifierProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void registerBiomeModifiers() {
        add(new Feature(SFDFeatures.SULPHUR, GenerationStep.Decoration.UNDERGROUND_ORES, BiomeTags.IS_OVERWORLD));
        add(new Feature(SFDFeatures.NETHER_SULPHUR, GenerationStep.Decoration.UNDERGROUND_ORES, BiomeTags.IS_NETHER));
        add(new Feature(SFDFeatures.DEEPSLATE_SULPHUR, GenerationStep.Decoration.UNDERGROUND_ORES, BiomeTags.IS_OVERWORLD));
        add(new Feature(SFDFeatures.NITER, GenerationStep.Decoration.UNDERGROUND_ORES, BiomeTags.IS_OVERWORLD));
    }
}
