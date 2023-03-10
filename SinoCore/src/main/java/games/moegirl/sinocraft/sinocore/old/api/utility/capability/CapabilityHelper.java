package games.moegirl.sinocraft.sinocore.old.api.utility.capability;

import games.moegirl.sinocraft.sinocore.api.SinoCoreAPI;
import games.moegirl.sinocraft.sinocore.api.capability.IPlayerCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityHelper {
    public static void clone(Player newPlayer, Player original,
                             ICapabilityProvider provider,
                             Capability<IPlayerCapability> capability) {
        var originalCap = getCap(original, provider, capability);
        var newCap = getCap(original, provider, capability);

        if (originalCap != null && newCap != null) {
            newCap.deserializeNBT(newCap.serializeNBT());
        }
    }

    private static IPlayerCapability getCap(Player player,
                                     ICapabilityProvider provider,
                                     Capability<IPlayerCapability> capability) {
        var capOptional = player.getCapability(capability);
        if (!capOptional.isPresent()) {
            capOptional = provider.getCapability(capability);

            if (!capOptional.isPresent()) {
                SinoCoreAPI.LOGGER.warn("Cannot get capability for " + capability.getName());
                return null;
            }
        }

        return capOptional.orElseThrow(NullPointerException::new);
    }
}
