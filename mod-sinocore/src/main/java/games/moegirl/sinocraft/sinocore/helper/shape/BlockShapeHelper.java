package games.moegirl.sinocraft.sinocore.helper.shape;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.concurrent.atomic.AtomicReference;

public class BlockShapeHelper {

    /**
     * Clockwise rotate a voxel shape in the X axis.
     * @param shape VoxelShape
     * @return Rotated
     */
    public static VoxelShape rotateX(VoxelShape shape) {
        var result = Shapes.empty();
        // Todo: qyl27: check it, really clockwise?
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                Shapes.or(result, Shapes.create(minX, 1 - maxZ, minY, maxX, 1 - minZ, maxY)));
        return result;
    }

    /**
     * Clockwise rotate a voxel shape in the Y axis.
     * @param shape VoxelShape
     * @return Rotated
     */
    public static VoxelShape rotateY(VoxelShape shape) {
        AtomicReference<VoxelShape> result = new AtomicReference<>(Shapes.empty());
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                result.set(Shapes.or(result.get(), Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))));
        return result.get();
    }
}
