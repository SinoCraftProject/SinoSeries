package games.moegirl.sinocraft.sinodivination.old.plugin.top;

import games.moegirl.sinocraft.sinodivination.old.blockentity.ISophoraEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SophoraChestEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SophoraEntity;
import games.moegirl.sinocraft.sinodivination.old.data.SDLangKeys;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

// todo jei support
public enum SophoraBlockDisplayOverride /*implements IBlockDisplayOverride*/ {

    INSTANCE;

//    @Override
//    public boolean overrideStandardInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
//        if (SinoCoreAPI.DEBUG && level.getBlockEntity(iProbeHitData.getPos()) instanceof ISophoraEntity sophora) {
//            if (sophora instanceof SophoraEntity se) {
//                iProbeInfo.text(SDLangKeys.TOP_BLOCK_OWNER, TOPPlugins.getPlayerName(se.getEntity(), level));
//            } else if (sophora instanceof SophoraChestEntity sce) {
//                Optional<SophoraChestEntity.PlayerRecord> recordSup = sce.getEntity();
//                if (recordSup.isPresent()) {
//                    SophoraChestEntity.PlayerRecord record = recordSup.get();
//                    iProbeInfo.text(SDLangKeys.TOP_BIRTHDAY, record.name(), record.birthday());
//                } else {
//                    iProbeInfo.text(SDLangKeys.TOP_BIRTHDAY_NO);
//                }
//            }
//            return true;
//        }
//        return false;
//    }
}
