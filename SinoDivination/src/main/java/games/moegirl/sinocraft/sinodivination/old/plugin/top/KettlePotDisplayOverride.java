package games.moegirl.sinocraft.sinodivination.old.plugin.top;

import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

// todo jei support
public enum KettlePotDisplayOverride /*implements IBlockDisplayOverride*/ {

    INSTANCE;

//    @Override
//    public boolean overrideStandardInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
//        if (SinoCoreAPI.DEBUG && blockState.getBlock() == SDBlocks.KETTLE_POT.get()) {
//            SDBlockEntities.getKettlePot(level, iProbeHitData.getPos()).ifPresent(pot -> iProbeInfo.text("Inputs: ")
//                    .item(pot.getInput().getStackInSlot(0))
//                    .item(pot.getInput().getStackInSlot(1))
//                    .item(pot.getInput().getStackInSlot(2))
//                    .text("Fluid: " + pot.getTank().getFluid().getFluid().getRegistryName() + " " + pot.getTank().getFluid().getAmount())
//                    .progress(pot.getProgress(), 101)
//                    .text("Status: " + pot.getStatus())
//                    .text("Output: ")
//                    .item(pot.getResult())
//                    .text("Recipe: " + pot.getRecipe().map(Recipe::getId).map(ResourceLocation::toString).orElse("null")));
//        }
//        return false;
//    }
}
