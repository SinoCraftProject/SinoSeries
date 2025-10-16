package games.moegirl.sinocraft.sinocore.helper.shape;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;

public class VoxelShapeHelper {

    /**
     * Combine VoxelShapes.
     * @param shapes VoxelShapes to combine.
     * @return Combined.
     */
    public static VoxelShape combine(VoxelShape... shapes) {
        if (shapes.length > 1) {
            var first = shapes[0];
            return Arrays.stream(shapes).reduce(first, Shapes::or);
        } else if (shapes.length == 0) {
            return Shapes.empty();
        } else {
            return shapes[0];
        }
    }

    /**
     * Exclude shapes from base.
     * @param base Base VoxelShape.
     * @param shapes VoxelShapes to exclude.
     * @return Excluded.
     */
    public static VoxelShape exclude(VoxelShape base, VoxelShape... shapes) {
        return Arrays.stream(shapes).reduce(base, (s1, s2) -> Shapes.join(s1, s2, BooleanOp.ONLY_FIRST));
    }
}
