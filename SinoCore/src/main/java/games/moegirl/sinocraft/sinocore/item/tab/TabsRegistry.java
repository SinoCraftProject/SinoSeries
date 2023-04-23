package games.moegirl.sinocraft.sinocore.item.tab;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabsRegistry {

    /**
     * Map<ResourceLocation resourceLocation, Tuple<CreativeModeTab tab, String translationKey> tuple>.
     */
    public static final Map<ResourceLocation, Tuple<CreativeModeTab, String>> CREATIVE_MODE_TABS = new HashMap<>();

    private static final Map<ResourceLocation, List<DeferredRegister<Item>>> DEFERRED_REGISTERS = new HashMap<>();
    private static final Map<ResourceLocation, List<ItemStack>> REGISTRY_LIST = new HashMap<>();

    private static String makeTranslateKey(ResourceLocation loc) {
        return "tab." + loc.getNamespace() + "." + loc.getPath();
    }

    @SubscribeEvent
    public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register event) {
        // Fixme: qyl27: bad performance.
        for (var deferredRegisters : DEFERRED_REGISTERS.entrySet()) {
            for (var deferredRegister : deferredRegisters.getValue()) {
                for (var registryItem : deferredRegister.getEntries()) {
                    var item = registryItem.get();
                    if (item instanceof ITabItem tabItem) {
                        tabItem.getTabs().forEach(id -> {
                            if (!hasRegistered(id, item)) {
                                addToList(id, new ItemStack(item));
                            }
                        });
                    } else {
                        var resourceLocation = deferredRegisters.getKey();

                        if (!hasRegistered(resourceLocation, item)) {
                            addToList(resourceLocation, new ItemStack(item));
                        }
                    }
                }
            }
        }

        for (var entry : REGISTRY_LIST.entrySet()) {
            var key = entry.getKey();
            event.registerCreativeModeTab(key, builder -> {
                var translationKey = makeTranslateKey(entry.getKey());
                builder.title(Component.translatable(translationKey))
                        .icon(() -> entry.getValue().stream().findFirst().orElse(new ItemStack(Items.APPLE)))
                        .displayItems((flagSet, output) -> output.acceptAll(new ArrayList<>(entry.getValue())));
                var result = builder.build();
                CREATIVE_MODE_TABS.put(key, new Tuple<>(result, translationKey));
            });
        }
    }

    public static void addToList(ResourceLocation resourceLocation, ItemStack stack) {
        if (!REGISTRY_LIST.containsKey(resourceLocation)) {
            REGISTRY_LIST.put(resourceLocation, new ArrayList<>());
        }

        REGISTRY_LIST.get(resourceLocation).add(stack);
    }

    private static boolean hasRegistered(ResourceLocation resourceLocation, Item item) {
        if (REGISTRY_LIST.containsKey(resourceLocation)) {
            return REGISTRY_LIST.get(resourceLocation).stream().anyMatch(i -> i.is(item));
        }
        return false;
    }

    public static void addDeferredRegister(ResourceLocation defaultTab,
                                           DeferredRegister<Item> deferredRegister) {
        if (!DEFERRED_REGISTERS.containsKey(defaultTab)) {
            DEFERRED_REGISTERS.put(defaultTab, new ArrayList<>());
        }

        DEFERRED_REGISTERS.get(defaultTab).add(deferredRegister);
    }
}
