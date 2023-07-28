package games.moegirl.sinocraft.sinofeast.capability;

import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.capability.entity.player_preference.IPlayerFoodPreference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SFCapabilities {
    public static final ResourceLocation PLAYER_FOOD_PREFERENCE_NAME = new ResourceLocation(SinoFeast.MODID, "player_food_preference");

    public static final Capability<IPlayerFoodPreference> PLAYER_FOOD_PREFERENCE = CapabilityManager.get(new CapabilityToken<>() {
    });
}
