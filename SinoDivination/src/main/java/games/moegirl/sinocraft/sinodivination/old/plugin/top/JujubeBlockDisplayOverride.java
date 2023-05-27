package games.moegirl.sinocraft.sinodivination.old.plugin.top;

// todo jei support
public enum JujubeBlockDisplayOverride /*implements IBlockDisplayOverride */{

    INSTANCE;
//
//    @Override
//    public boolean overrideStandardInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData data) {
//        if (SinoCoreAPI.DEBUG) {
//            Block block = blockState.getBlock();
//            if (block == SDBlocks.JUJUBE_CHEST.get()) {
//                BlockPos pos = data.getPos();
//                info.text("Has Sign: " + level.hasNeighborSignal(pos));
//                ChestType value = blockState.getValue(JujubeChest.TYPE);
//                Direction direction = blockState.getValue(JujubeChest.FACING);
//                if (value == ChestType.LEFT) {
//                    info.text(value.name() + ": " + level.hasNeighborSignal(pos.relative(direction.getClockWise())));
//                } else if (value == ChestType.RIGHT) {
//                    info.text(value.name() + ": " + level.hasNeighborSignal(pos.relative(direction.getCounterClockWise())));
//                }
//            } else {
//                boolean isOpen;
//                if (block == SDWoodwork.JUJUBE.door()) {
//                    isOpen = blockState.getValue(DoorBlock.OPEN);
//                } else if (block == SDWoodwork.JUJUBE.trapdoor()) {
//                    isOpen = blockState.getValue(TrapDoorBlock.OPEN);
//                } else if (block == SDWoodwork.JUJUBE.fenceGate()) {
//                    isOpen = blockState.getValue(FenceGateBlock.OPEN);
//                } else {
//                    return false;
//                }
//                info.text("Status: " + (isOpen ? "Open" : "Closed"));
//                info.text("Power: " + level.getSignal(data.getPos(), data.getSideHit()));
//            }
//            return true;
//        }
//        return false;
//    }
}
