package games.moegirl.sinocraft.sinodeco.neoforge;

import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(SinoDeco.MODID)
public class SinoDecoNeoForge {
    public SinoDecoNeoForge(IEventBus bus, ModContainer modContainer) {
        SinoDeco.init();
    }
}
