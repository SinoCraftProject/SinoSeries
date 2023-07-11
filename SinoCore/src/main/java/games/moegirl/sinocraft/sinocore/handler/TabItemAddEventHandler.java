package games.moegirl.sinocraft.sinocore.handler;

import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabItemAddEventHandler {

    private static final Map<ResourceKey<CreativeModeTab>, TabItemGenerator> GENERATORS = Collections.synchronizedMap(new HashMap<>());

    /**
     * 用于 CreativeModeTab 实例，用于 Minecraft 原版 Tab。该方法获取的 Generator 仅 add，addStack 有效
     *
     * @param tab tab
     * @return TabItemGenerator
     */
    public static TabItemGenerator forCreativeModeTab(ResourceKey<CreativeModeTab> tab) {
        return GENERATORS.computeIfAbsent(tab, t -> new TabItemGenerator() {
            @Override
            public synchronized TabItemGenerator setIcon(Supplier<ItemStack> icon) {
                throw new IllegalCallerException("CreativeTab " + tab + " not support operation: set display item");
            }
        });
    }

    @SubscribeEvent
    public static void onAddItem(BuildCreativeModeTabContentsEvent event) {
        GENERATORS.computeIfPresent(event.getTabKey(), (key, generator) -> {
            generator.accept(event.getParameters(), event);
            return generator;
        });
    }
}
