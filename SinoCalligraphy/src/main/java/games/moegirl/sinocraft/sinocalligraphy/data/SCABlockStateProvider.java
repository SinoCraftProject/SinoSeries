package games.moegirl.sinocraft.sinocalligraphy.data;

import games.moegirl.sinocraft.sinocalligraphy.block.PaperDryingRackBlock;
import games.moegirl.sinocraft.sinocalligraphy.block.SCABlocks;
import games.moegirl.sinocraft.sinocore.data.BlockStateProviderBase;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SCABlockStateProvider extends BlockStateProviderBase {
    public SCABlockStateProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper,
                                 DeferredRegister<? extends Block>... deferredRegisters) {
        super(output, modId, existingFileHelper, deferredRegisters);
    }

    @Override
    protected void registerBlockStatesAndModels() {
        var paperDryingRackBuilder = getMultipartBuilder(SCABlocks.PAPER_DRYING_RACK.get());
        for (int i = 0; i <= 4; i++) {
            var direction = Direction.NORTH;
            for (int j = 0; j < 4; j++) {
                paperDryingRackBuilder.part().modelFile(models().getExistingFile(modLoc("block/paper_drying_rack_" + i)))
                        .rotationY(90 * j)
                        .addModel()
                        .condition(PaperDryingRackBlock.PROCESS, i)
                        .condition(PaperDryingRackBlock.FACING, direction)
                        .end();
                direction = direction.getClockWise();
            }
        }
    }
}
