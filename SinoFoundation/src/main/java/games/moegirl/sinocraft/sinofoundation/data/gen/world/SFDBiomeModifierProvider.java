package games.moegirl.sinocraft.sinofoundation.data.gen.world;

import games.moegirl.sinocraft.sinocore.data.gen.world.AbstractBiomeModifierProvider;
import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDBiomeTags;
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

        add(new Feature(SFDFeatures.BLACK_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.BLACK_JADE_GENERATION));
        add(new Feature(SFDFeatures.DEEPSLATE_BLACK_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.BLACK_JADE_GENERATION));
        add(new Feature(SFDFeatures.GREEN_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.GREEN_JADE_GENERATION));
        add(new Feature(SFDFeatures.DEEPSLATE_GREEN_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.GREEN_JADE_GENERATION));
        add(new Feature(SFDFeatures.RED_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.RED_JADE_GENERATION));
        add(new Feature(SFDFeatures.DEEPSLATE_RED_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.RED_JADE_GENERATION));
        add(new Feature(SFDFeatures.WHITE_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.WHITE_JADE_GENERATION));
        add(new Feature(SFDFeatures.DEEPSLATE_WHITE_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.WHITE_JADE_GENERATION));
        add(new Feature(SFDFeatures.YELLOW_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.YELLOW_JADE_GENERATION));
        add(new Feature(SFDFeatures.DEEPSLATE_YELLOW_JADE, GenerationStep.Decoration.UNDERGROUND_ORES, SFDBiomeTags.YELLOW_JADE_GENERATION));
    }
}
