package games.moegirl.sinocraft.sinobrush.stat;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.registry.ICustomStatRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.resources.ResourceLocation;

public class SBRStats {
    public static ICustomStatRegistry STATS = RegistryManager.createCustomStat(SinoBrush.MODID);

    public static void register() {
        STATS.register();
    }

    public static final ResourceLocation UNFOLD_FAN = STATS.register("unfold_fan");
    public static final ResourceLocation DRAW_BY_BRUSH = STATS.register("draw_by_brush");
}
