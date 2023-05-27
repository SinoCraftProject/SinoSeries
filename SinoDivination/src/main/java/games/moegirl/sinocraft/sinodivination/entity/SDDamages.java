package games.moegirl.sinocraft.sinodivination.entity;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class SDDamages {

    public static final ResourceKey<DamageType> SOPHORA_DOOR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SinoDivination.MODID, "sophora_door"));
}
