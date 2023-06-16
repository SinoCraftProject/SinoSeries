package games.moegirl.sinocraft.sinocore.tab;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luqin2007
 */
public class TabsRegistry {

    private static final Map<ResourceLocation, TabItemGenerator> GENERATORS = new HashMap<>();

    /**
     * 要添加物品从这里拿 TabItemGenerator
     * @param tab tab
     * @return TabItemGenerator
     */
    public static TabItemGenerator items(RegistryObject<CreativeModeTab> tab) {
        return GENERATORS.get(tab.getId());
    }

    // =================================================================================================================

    /**
     * 注册 Tab
     * @param register DR
     * @param name tab 名称
     * @param generator TabItemGenerator
     * @return RegistryObject<CreativeModeTab>
     */
    public static RegistryObject<CreativeModeTab> tab(String modid, DeferredRegister<CreativeModeTab> register, String name, TabItemGenerator generator) {
        RegistryObject<CreativeModeTab> object = register.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("tab." + modid + "." + name))
                .displayItems(generator)
                .icon(generator::displayItem)
                .build());
        GENERATORS.put(object.getId(), generator);
        return object;
    }
}
