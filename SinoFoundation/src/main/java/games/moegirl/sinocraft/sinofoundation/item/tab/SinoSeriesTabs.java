package games.moegirl.sinocraft.sinofoundation.item.tab;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SinoSeriesTabs {
    public static final ResourceLocation BUILDING_BLOCKS = new ResourceLocation(SinoFoundation.MODID, "building_blocks");
    public static final ResourceLocation FUNCTIONAL_BLOCKS = new ResourceLocation(SinoFoundation.MODID, "functional_blocks");
    public static final ResourceLocation AGRICULTURE = new ResourceLocation(SinoFoundation.MODID, "agriculture");
    public static final ResourceLocation TOOLS = new ResourceLocation(SinoFoundation.MODID, "tools");
    public static final ResourceLocation WEAPONS = new ResourceLocation(SinoFoundation.MODID, "weapons");
    public static final ResourceLocation MISC = new ResourceLocation(SinoFoundation.MODID, "misc");

    private static final Map<ResourceLocation, List<DeferredRegister<Item>>> DEFERRED_REGISTERS = new HashMap<>();
    private static final Map<ResourceLocation, List<Item>> REGISTRY_LIST = new HashMap<>();

    private static String makeTranslateKey(ResourceLocation loc) {
        return "tab." + loc.getNamespace() + "." + loc.getPath();
    }

    @SubscribeEvent
    public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register event) {
        for (var deferredRegisters : DEFERRED_REGISTERS.entrySet()) {
            for (var deferredRegister : deferredRegisters.getValue()) {
                for (var registryItem : deferredRegister.getEntries()) {
                    var item = registryItem.get();
                    if (item instanceof ITabItem tabItem) {
                        addToList(tabItem.getTab(), item);
                    } else {
                        addToList(deferredRegisters.getKey(), item);
                    }
                }
            }
        }

        for (var entry : REGISTRY_LIST.entrySet()) {
            event.registerCreativeModeTab(entry.getKey(), builder ->
                builder.title(Component.translatable(makeTranslateKey(entry.getKey())))
                        .icon(() -> new ItemStack(Items.APPLE))
                        .displayItems((flagSet, output, hasPermission) -> {
                            output.acceptAll(entry.getValue().stream()
                                    .map(ItemStack::new)
                                    .collect(Collectors.toList()));
                        })
                        .build());
        }
    }

    private static void addToList(ResourceLocation resourceLocation, Item item) {
        if (!REGISTRY_LIST.containsKey(resourceLocation)) {
            REGISTRY_LIST.put(resourceLocation, new ArrayList<>());
        }

        REGISTRY_LIST.get(resourceLocation).add(item);
    }

    // Todo: qyl27: move to SinoCore later.
    public static void addDeferredRegister(ResourceLocation resourceLocation,
                                           DeferredRegister<Item> deferredRegister) {
        if (!DEFERRED_REGISTERS.containsKey(resourceLocation)) {
            DEFERRED_REGISTERS.put(resourceLocation, new ArrayList<>());
        }

        DEFERRED_REGISTERS.get(resourceLocation).add(deferredRegister);
    }
}
