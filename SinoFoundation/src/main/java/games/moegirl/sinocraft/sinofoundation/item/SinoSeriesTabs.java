package games.moegirl.sinocraft.sinofoundation.item;

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
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SinoSeriesTabs {
    public static final ResourceLocation BUILDING_BLOCKS_NAME = new ResourceLocation(SinoFoundation.MODID, "building_blocks");

    public static final String BUILDING_BLOCKS_TRANSLATE_KEY = makeTranslateKey(BUILDING_BLOCKS_NAME);

    private static final List<RegistryObject<Item>> BUILDING_BLOCKS_LIST = new ArrayList<>();

    private static String makeTranslateKey(ResourceLocation loc) {
        return "tab." + loc.getNamespace() + "." + loc.getPath();
    }

    @SubscribeEvent
    public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(BUILDING_BLOCKS_NAME, builder ->
                builder.title(Component.translatable(BUILDING_BLOCKS_TRANSLATE_KEY))
                        .icon(() -> new ItemStack(Items.APPLE)) // Todo: qyl27: change it.
                        .displayItems((flagSet, output, hasPermission) -> {
                            output.acceptAll(BUILDING_BLOCKS_LIST.stream()
                                    .map(e -> new ItemStack(e.get()))
                                    .collect(Collectors.toList()));
                        })
                        .build());


    }

    // Todo: qyl27: maybe better api?
    public static void addBuildingBlock(DeferredRegister<Item> register) {
        BUILDING_BLOCKS_LIST.addAll(register.getEntries());
    }
}
