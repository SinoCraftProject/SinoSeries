package games.moegirl.sinocraft.sinofoundation;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

/**
 * @author luqin2007
 */
public class SFDDamages {

    public static final ResourceKey<DamageType> SOPHORA_DOOR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(SinoFoundation.MODID, "sophora_door"));

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SOPHORA_DOOR, new DamageType(SinoFoundation.MODID + ".damage.sophora_door", DamageScaling.ALWAYS, 0));
    }
}
