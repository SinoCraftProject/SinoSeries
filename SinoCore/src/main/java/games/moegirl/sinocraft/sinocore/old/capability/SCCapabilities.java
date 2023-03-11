package games.moegirl.sinocraft.sinocore.old.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * All capability
 */
public class SCCapabilities {

    public static final Capability<IQuizzingPlayer> QUIZZING_PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
}
