package games.moegirl.sinocraft.sinofoundation.old.plugin.top;

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
