package games.moegirl.sinocraft.sinocore.utility.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;

import java.util.Objects;

public class ModBusHelper {
    public static IEventBus getModBus(String modId) {
        if ("minecraft".equals(modId)) {
            return getModBus("sinocore");
        }

        return Objects.requireNonNull(ModList.get().getModContainerById(modId).get().getEventBus());
    }
}
