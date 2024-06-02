package games.moegirl.sinocraft.sinocore.utility.block.connection;

import net.minecraft.core.Direction;

public record ConnectUpdateResult(Direction facing, ConnectionState state) {
}
