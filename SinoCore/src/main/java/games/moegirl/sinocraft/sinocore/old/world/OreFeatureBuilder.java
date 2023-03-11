package games.moegirl.sinocraft.sinocore.old.world;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A builder for ore generator
 * <p>This util used to build feature use {@link Feature#ORE}</p>
 */
public class OreFeatureBuilder extends BaseFeatureBuilder<OreConfiguration, OreFeatureBuilder> {

    private final List<OreConfiguration.TargetBlockState> targets = new LinkedList<>();
    private float discardChanceOnAirExposure;
    private int size;

    public OreFeatureBuilder() {
        super(Feature.ORE);
    }

    /**
     * The ore size
     *
     * <table>
     *     <tr>
     *         <th>ore</th>
     *         <th>size</th>
     *     </tr>
     *     <tr>
     *         <th>coal</th>
     *         <th>17</th>
     *     </tr>
     *     <tr>
     *         <th>iron(small)</th>
     *         <th>4</th>
     *     </tr>
     *     <tr>
     *         <th>iron</th>
     *         <th>9</th>
     *     </tr>
     *     <tr>
     *         <th>copper(small)</th>
     *         <th>10</th>
     *     </tr>
     *     <tr>
     *         <th>copper(large)</th>
     *         <th>20</th>
     *     </tr>
     *     <tr>
     *         <th>gold</th>
     *         <th>9</th>
     *     </tr>
     *     <tr>
     *         <th>gold(nether)</th>
     *         <th>10</th>
     *     </tr>
     *     <tr>
     *         <th>diamond(small)</th>
     *         <th>4</th>
     *     </tr>
     *     <tr>
     *         <th>diamond</th>
     *         <th>8</th>
     *     </tr>
     *     <tr>
     *         <th>diamond(large)</th>
     *         <th>12</th>
     *     </tr>
     *     <tr>
     *         <th>emerald</th>
     *         <th>3</th>
     *     </tr>
     *     <tr>
     *         <th>clay</th>
     *         <th>33</th>
     *     </tr>
     *     <tr>
     *         <th>lapis</th>
     *         <th>7</th>
     *     </tr>
     *     <tr>
     *         <th>redstone</th>
     *         <th>8</th>
     *     </tr>
     *     <tr>
     *         <th>quartz</th>
     *         <th>14</th>
     *     </tr>
     * </table>
     *
     * @param size ore size
     * @return this builder
     */
    public OreFeatureBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * A chance to skip in air, 0 is always skip, 1 is always generated, default is 0.
     *
     * <table>
     *     <tr>
     *         <th>ore</th>
     *         <th>size</th>
     *     </tr>
     *     <tr>
     *         <th>coal(buried)</th>
     *         <th>0.5</th>
     *     </tr>
     *     <tr>
     *         <th>gold(buried)</th>
     *         <th>0.5</th>
     *     </tr>
     *     <tr>
     *         <th>diamond(small)</th>
     *         <th>0.5</th>
     *     </tr>
     *     <tr>
     *         <th>diamond(buried)</th>
     *         <th>1.0</th>
     *     </tr>
     *     <tr>
     *         <th>diamond(large)</th>
     *         <th>0.7</th>
     *     </tr>
     *     <tr>
     *         <th>lapis(buried)</th>
     *         <th>1.0</th>
     *     </tr>
     * </table>
     *
     * @param chance generated chance
     * @return this builder
     */
    public OreFeatureBuilder discardChanceOnAirExposure(float chance) {
        this.discardChanceOnAirExposure = chance;
        return this;
    }

    // Target

    /**
     * Add a rule to replace existed block to ore
     *
     * @param target replace rule
     * @return this builder
     */
    public OreFeatureBuilder addReplaceRule(OreConfiguration.TargetBlockState target) {
        targets.add(target);
        return this;
    }

    /**
     * Add a rule to replace existed block to ore
     *
     * @param rule     replace rule
     * @param replaced new block
     * @return this builder
     */
    public OreFeatureBuilder addReplaceRule(RuleTest rule, BlockState replaced) {
        return addReplaceRule(OreConfiguration.target(rule, replaced));
    }

    /**
     * Add a rule to replace existed block to ore
     *
     * @param rule     replace rule
     * @param replaced new block
     * @return this builder
     */
    public OreFeatureBuilder addReplaceRule(RuleTest rule, Block replaced) {
        return addReplaceRule(OreConfiguration.target(rule, replaced.defaultBlockState()));
    }

    /**
     * Add a rule to replace existed block to ore
     *
     * @param rule     replace rule
     * @param replaced new block
     * @return this builder
     */
    public OreFeatureBuilder addReplaceRule(RuleTest rule, Supplier<Block> replaced) {
        return addReplaceRule(rule, replaced.get());
    }

    /**
     * A simple method for stone ore
     *
     * @param ore ore block
     * @return this builder
     */
    public OreFeatureBuilder addStoneOre(Supplier<? extends Block> ore) {
        return addReplaceRule(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.get().defaultBlockState());
    }

    @Override
    public OreFeatureBuilder fromConfiguration(OreConfiguration parent) {
        discardChanceOnAirExposure = parent.discardChanceOnAirExposure;
        size = parent.size;
        return this;
    }

    @Override
    protected OreConfiguration buildConfiguration() {
        return new OreConfiguration(targets, size, discardChanceOnAirExposure);
    }
}
