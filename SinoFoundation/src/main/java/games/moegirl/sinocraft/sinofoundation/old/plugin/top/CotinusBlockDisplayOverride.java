package games.moegirl.sinocraft.sinofoundation.old.plugin.top;

// todo jei support
public enum CotinusBlockDisplayOverride /*implements IBlockDisplayOverride*/ {

    INSTANCE;
//
//    @Override
//    public boolean overrideStandardInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
//        BlockEntity entity = level.getBlockEntity(iProbeHitData.getPos());
//        if (entity instanceof ICotinusEntity cotinus) {
//            OwnerChecker owner = cotinus.owner();
//            info.text(SDLangKeys.TOP_BLOCK_OWNER, TOPPlugins.getPlayerName(owner.getOwner(), level));
//            Set<UUID> allowed = owner.getAllowed();
//            if (!allowed.isEmpty()) {
//                info.text(SDLangKeys.TOP_BLOCK_ALLOWED, allowed.size());
//                if (probeMode != ProbeMode.NORMAL) {
//                    for (UUID uuid : allowed) {
//                        info.text(TOPPlugins.getPlayerName(uuid, level));
//                    }
//                }
//            }
//            return true;
//        }
//        return false;
//    }
}
