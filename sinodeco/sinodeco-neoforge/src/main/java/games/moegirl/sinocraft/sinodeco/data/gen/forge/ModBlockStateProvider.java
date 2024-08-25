package games.moegirl.sinocraft.sinodeco.data.gen.forge;

import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.WoodenTableBlock;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        {
            var builder = getMultipartBuilder(SDBlocks.WOODEN_TABLE.get());

            for (var i = 0; i < WoodenTableBlock.PARTS.length; i++) {
                var north = (i & 0b1000) != 0;
                var east = (i & 0b0100) != 0;
                var south = (i & 0b0010) != 0;
                var west = (i & 0b0001) != 0;

                var extras = WoodenTableBlock.PARTS[i];
                for (var parts : extras) {
                    if (parts == null) {
                        continue;
                    }

                    for (var p : parts) {
                        if (p == null) {
                            continue;
                        }

                        var part = builder.part()
                                .modelFile(models().getExistingFile(modLoc("block/wooden_table_" + p.name())))
                                .rotationY(p.rotate())
                                .addModel()
                                .condition(WoodenTableBlock.NORTH, north)
                                .condition(WoodenTableBlock.EAST, east)
                                .condition(WoodenTableBlock.SOUTH, south)
                                .condition(WoodenTableBlock.WEST, west);

                        if (p.condition() != null) {
                            part.condition(p.condition(), p.conditionValue());
                        }
                    }
                }
            }
        }

        {
            simpleBlock(SDBlocks.MARBLE_BLOCK.get(), models().cubeColumn("marble_block", modLoc("block/marble_block"), modLoc("block/marble_block_top")));
            simpleBlock(SDBlocks.CHISELED_MARBLE.get(), models().cubeColumn("chiseled_marble", modLoc("block/chiseled_marble"), modLoc("block/chiseled_marble_top")));
            axisBlock(SDBlocks.MARBLE_PILLAR.get(), modLoc("block/marble_pillar"), modLoc("block/marble_pillar_top"));
            stairsBlock(SDBlocks.MARBLE_STAIRS.get(), modLoc("block/marble_block"), modLoc("block/marble_block_top"), modLoc("block/marble_block_top"));
            slabBlock(SDBlocks.MARBLE_SLAB.get(), modLoc("block/marble_block"), modLoc("block/marble_block"));
            simpleBlock(SDBlocks.SMOOTH_MARBLE.get());
            stairsBlock(SDBlocks.SMOOTH_MARBLE_STAIRS.get(), modLoc("block/smooth_marble"), modLoc("block/smooth_marble_top"), modLoc("block/smooth_marble_top"));
            slabBlock(SDBlocks.SMOOTH_MARBLE_SLAB.get(), modLoc("block/smooth_marble"), modLoc("block/smooth_marble"));
            simpleBlock(SDBlocks.MARBLE_BRICKS.get());
        }
    }
}
