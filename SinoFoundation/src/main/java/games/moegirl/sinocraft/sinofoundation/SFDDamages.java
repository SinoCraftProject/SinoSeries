package games.moegirl.sinocraft.sinofoundation;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

/**
 * @author luqin2007
 */
public class SFDDamages {

    public static final ResourceKey<DamageType> SOPHORA_DOOR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SinoFoundation.MODID, "sophora_door"));

}
