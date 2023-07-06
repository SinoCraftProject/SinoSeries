package games.moegirl.sinocraft.sinocore.utility;

import games.moegirl.sinocraft.sinocore.capability.SCCapabilities;
import games.moegirl.sinocraft.sinocore.capability.entity.armor_stand.SummonedArmorStand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ArmorStandHelper {
    public static final byte ARMOR_STAND_DATA_FLAGS = 1 | 8 | 16;

    public static ArmorStand createDummyArmorStand(Level level, Vec3 pos) {
        var armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
        armorStand.getEntityData().set(ArmorStand.DATA_CLIENT_FLAGS, (byte) (armorStand.getEntityData().get(ArmorStand.DATA_CLIENT_FLAGS) | ARMOR_STAND_DATA_FLAGS));
        armorStand.setInvisible(true);
        armorStand.setPos(pos);
        armorStand.setNoGravity(true);
        armorStand.setInvulnerable(true);

        var capOptional = armorStand.getCapability(SCCapabilities.SUMMONED_ARMOR_STAND);
        var cap = capOptional.orElse(new SummonedArmorStand());
        cap.setShouldDeleteAfterDismount(true);

        level.addFreshEntity(armorStand);

        return armorStand;
    }
}
