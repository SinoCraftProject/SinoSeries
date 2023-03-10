package games.moegirl.sinocraft.sinocore.old.api.utility.shape;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapeHelper {
    // Todo: qyl27: move from sfd.
    public static VoxelShape rotateHorizontal(VoxelShape shape, Direction from, Direction to) {
        var rotates = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        for (var i = 0; i < rotates; i++) {
            shape = rotateClockwise(shape);
        }

        return shape;
    }

    public static VoxelShape rotateClockwise(VoxelShape shape) {
        var buffer = new VoxelShape[] { shape, Shapes.empty() };

        buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
        buffer[0] = buffer[1];
        buffer[1] = Shapes.empty();

        return buffer[0];
    }
}
