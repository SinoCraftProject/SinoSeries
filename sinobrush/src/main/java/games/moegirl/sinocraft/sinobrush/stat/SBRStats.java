package games.moegirl.sinocraft.sinobrush.stat;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class SBRStats {
    public static IRegistry<ResourceLocation> STATS = RegistryManager.obtain(SinoBrush.MODID, Registries.CUSTOM_STAT);

    public static void register() {
        STATS.register();
    }

    public static final Supplier<ResourceLocation> UNFOLD_FAN = STATS.register("unfold_fan", () -> new ResourceLocation(SinoBrush.MODID, "unfold_fan"));
    public static final Supplier<ResourceLocation> DRAW_BY_BRUSH = STATS.register("draw_by_brush", () -> new ResourceLocation(SinoBrush.MODID, "draw_by_brush"));
}
