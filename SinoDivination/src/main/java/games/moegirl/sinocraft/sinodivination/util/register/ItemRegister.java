package games.moegirl.sinocraft.sinodivination.util.register;

import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * 注册 Item 及 Tab
 *
 * @author luqin2007
 */
public class ItemRegister extends DeferredRegisters<Item> {

    public ItemRegister(String modid) {
        super(modid);
    }

    @SafeVarargs
    public final <I extends Item> RegistryObject<I> register(String name, Supplier<? extends I> sup, RegistryObject<CreativeModeTab>... tabs) {
        return getRegister().register(name, () -> {
            I item = sup.get();
            for (RegistryObject<CreativeModeTab> tab : tabs) {
                TabsRegistry.items(tab).addItem(item);
            }
            return item;
        });
    }

    @Override
    protected DeferredRegister<Item> create(String modid) {
        return DeferredRegister.create(ForgeRegistries.ITEMS, modid);
    }
}
