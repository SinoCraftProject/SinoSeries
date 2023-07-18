package games.moegirl.sinocraft.sinofeast.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.model.AbstractBlockStateProvider;
import games.moegirl.sinocraft.sinocore.data.gen.model.BaseAutoBlockStateProvider;
import games.moegirl.sinocraft.sinofeast.block.SFBlocks;
import games.moegirl.sinocraft.sinofeast.block.TeaTreeBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SFBlockStateProvider extends BaseAutoBlockStateProvider {
    @SafeVarargs
    public SFBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper, DeferredRegister<? extends Block>... registers) {
        super(output, modid, exFileHelper, registers);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        skipBlock(SFBlocks.TEA_TREE_BLOCK.get());

        addTeaTreeBlock();
    }

    private void addTeaTreeBlock() {
        getMultipartBuilder(SFBlocks.TEA_TREE_BLOCK.get())
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_seedling_0"))).addModel().condition(TeaTreeBlock.STAGE, 0).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_seedling_1"))).addModel().condition(TeaTreeBlock.STAGE, 1).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_seedling_2"))).addModel().condition(TeaTreeBlock.STAGE, 2).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_seedling_3"))).addModel().condition(TeaTreeBlock.STAGE, 3).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_growing_0"))).addModel().condition(TeaTreeBlock.STAGE, 4).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_growing_1"))).addModel().condition(TeaTreeBlock.STAGE, 5).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_mature"))).addModel().condition(TeaTreeBlock.STAGE, 6).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_flourishing_0"))).addModel().condition(TeaTreeBlock.STAGE, 7).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_flourishing_1"))).addModel().condition(TeaTreeBlock.STAGE, 8).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_flowering_0"))).addModel().condition(TeaTreeBlock.STAGE, 9).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_flowering_1"))).addModel().condition(TeaTreeBlock.STAGE, 10).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_seed_0"))).addModel().condition(TeaTreeBlock.STAGE, 11).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_seed_1"))).addModel().condition(TeaTreeBlock.STAGE, 12).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_aging_0"))).addModel().condition(TeaTreeBlock.STAGE, 13).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_aging_1"))).addModel().condition(TeaTreeBlock.STAGE, 14).end()
                .part().modelFile(models().getExistingFile(modLoc("block/tea_tree_aging_2"))).addModel().condition(TeaTreeBlock.STAGE, 15).end();
    }
}
