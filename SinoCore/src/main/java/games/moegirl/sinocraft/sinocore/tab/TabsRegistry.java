package games.moegirl.sinocraft.sinocore.tab;

import games.moegirl.sinocraft.sinocore.event.TabItemAddEventHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * @author luqin2007
 */
public class TabsRegistry {

    private static final Map<ResourceKey<CreativeModeTab>, TabItemGenerator> GENERATORS = new HashMap<>();
    private static final List<ResourceLocation> ALL_TABS = new ArrayList<>();

    static {
        for (CreativeModeTab tab : CreativeModeTabs.tabs()) {
            if (tab != CreativeModeTabs.searchTab()) { // 搜索
                ResourceLocation key = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab);
                if (!Objects.equals(key, CreativeModeTabs.INVENTORY.location()) // 物品栏
                        && !Objects.equals(key, CreativeModeTabs.HOTBAR.location())) { // 已保存物品栏
                    ALL_TABS.add(key);
                }
            }
        }
    }

    /**
     * 要添加物品从这里拿 TabItemGenerator
     * @param tab tab
     * @return TabItemGenerator
     */
    public static TabItemGenerator items(RegistryObject<CreativeModeTab> tab) {
        if (tab.getKey() == null /* why key is nullable? */) {
            throw new NullPointerException("Tab key must not null");
        }
        return items(tab.getKey());
    }

    /**
     * 要添加物品从这里拿 TabItemGenerator
     * @param tab tab
     * @return TabItemGenerator
     */
    public static TabItemGenerator items(ResourceKey<CreativeModeTab> tab) {
        synchronized (TabsRegistry.class) {
            if (GENERATORS.containsKey(tab)) {
                return GENERATORS.get(tab);
            }
        }
        return TabItemAddEventHandler.forCreativeModeTab(tab);
    }

    // =================================================================================================================

    /**
     * 注册 Tab
     * @param register DR
     * @param name tab 名称
     * @return RegistryObject<CreativeModeTab>
     */
    public static RegistryObject<CreativeModeTab> tab(String modid, DeferredRegister<CreativeModeTab> register, String name) {
        TabItemGenerator generator = new TabItemGenerator();
        ResourceLocation[] tabs = ALL_TABS.toArray(ResourceLocation[]::new);
        RegistryObject<CreativeModeTab> object = register.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("tab." + modid + "." + name))
                .displayItems(generator)
                .icon(generator::displayItem)
                .withTabsBefore(tabs)
                .build());
        ALL_TABS.add(object.getId());
        GENERATORS.put(object.getKey(), generator);
        return object;
    }
}
