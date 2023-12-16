package games.moegirl.sinocraft.sinocore.utility;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapeHelper {
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

    public static VoxelShape rotateClockwise(VoxelShape shape, int times) {
        var result = shape;
        for (var i = 0; i < times; i++) {
            shape = rotateClockwise(shape);
        }
        return shape;
    }
}
