package games.moegirl.sinocraft.sinodivination.old.entity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class SDDamages {
    public static final String MSG_SOPHORA_DOOR = SinoDivination.MODID + ".damage.sophora_door";

    public static final String KEY_SOPHORA_DOOR = "death.attack." + MSG_SOPHORA_DOOR;

    public static DamageSource bySophoraDoor() {
        // todo damage source ?
        return new DamageSource(Holder.direct(new DamageType(MSG_SOPHORA_DOOR, 0)));
    }
}
