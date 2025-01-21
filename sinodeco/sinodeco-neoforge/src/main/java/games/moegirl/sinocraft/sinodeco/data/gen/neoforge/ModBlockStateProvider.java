package games.moegirl.sinocraft.sinodeco.data.gen.neoforge;

import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.WoodenTableBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    protected ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    protected String path(Block block) {
        return key(block).getPath();
    }

    @Override
    protected void registerStatesAndModels() {
        tableModels(SDBlocks.PEACH_WOOD_TABLE.get(), modLoc("block/peach_wood_table"));

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

        {
            var planks = modLoc("block/peach_planks");
//            simpleBlock(SDBlocks.PEACH_LEAVES.get()); // Todo: texture
//            simpleBlock(SDBlocks.PEACH_SAPLING.get());    // Todo: texture
            logBlock(SDBlocks.PEACH_LOG.get());
            simpleBlock(SDBlocks.PEACH_WOOD.get(), models().cubeAll("peach_wood", modLoc("block/peach_log")));
            logBlock(SDBlocks.STRIPPED_PEACH_LOG.get());
            simpleBlock(SDBlocks.STRIPPED_PEACH_WOOD.get(), models().cubeAll("stripped_peach_wood", modLoc("block/stripped_peach_log")));
            simpleBlock(SDBlocks.PEACH_PLANKS.get());
            stairsBlock(SDBlocks.PEACH_STAIRS.get(), planks);
            slabBlock(SDBlocks.PEACH_SLAB.get(), planks, planks);
            models().withExistingParent("peach_fence_inventory", mcLoc("block/fence_inventory")).texture("texture", planks);
            fenceBlock(SDBlocks.PEACH_FENCE.get(), planks);
            fenceGateBlock(SDBlocks.PEACH_FENCE_GATE.get(), planks);
            doorBlock(SDBlocks.PEACH_DOOR.get(), modLoc("block/peach_door_bottom"), modLoc("block/peach_door_top"));  // Todo: texture
            trapdoorBlock(SDBlocks.PEACH_TRAPDOOR.get(), modLoc("block/peach_trapdoor"), true);   // Todo: texture
            pressurePlateBlock(SDBlocks.PEACH_PRESSURE_PLATE.get(), planks);
            models().withExistingParent("peach_button_inventory", mcLoc("block/button_inventory")).texture("texture", planks);
            buttonBlock(SDBlocks.PEACH_BUTTON.get(), planks);
            signBlock(SDBlocks.PEACH_SIGN.get(), SDBlocks.PEACH_WALL_SIGN.get(), modLoc("entity/signs/peach"));
        }
    }

    private void tableModels(Block block, ResourceLocation texture) {
        var builder = getMultipartBuilder(block);

        models().withExistingParent("block/" + path(block), modLoc("block/wooden_table"))
                .texture("0", texture);

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
                            .modelFile(models()
                                    .withExistingParent("block/" + path(block) + "_" + p.name(), modLoc("block/wooden_table_" + p.name()))
                                    .texture("0", texture))
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
}
