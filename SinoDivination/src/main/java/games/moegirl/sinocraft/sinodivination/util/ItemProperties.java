package games.moegirl.sinocraft.sinodivination.util;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.tab.TabItemGenerator;
import games.moegirl.sinocraft.sinocore.tab.TabsRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luqin2007
 */
public record ItemProperties(Item.Properties properties) {

    private static final Logger LOGGER = LoggerFactory.getLogger("ItemProperties");

    public static final Map<Item.Properties, Pair<List<TabItemGenerator>, List<TabItemGenerator>>> MAP = new HashMap<>();

    public ItemProperties() {
        this(new Item.Properties());
    }

    public ItemProperties tab(RegistryObject<CreativeModeTab> tab) {
//        ((IItemPropertiesTab) properties).sinodivination$addTab(TabsRegistry.items(tab));
//        sinodivination$addTab.get().accept(properties, TabsRegistry.items(tab));
        MAP.computeIfAbsent(properties, p -> Pair.of(new ArrayList<>(), new ArrayList<>())).getFirst().add(TabsRegistry.items(tab));
        return this;
    }

    public ItemProperties tab(ResourceKey<CreativeModeTab> tab) {
//        ((IItemPropertiesTab) properties).sinodivination$addTab(TabsRegistry.items(tab));
//        sinodivination$addTab.get().accept(properties, TabsRegistry.items(tab));
        MAP.computeIfAbsent(properties, p -> Pair.of(new ArrayList<>(), new ArrayList<>())).getFirst().add(TabsRegistry.items(tab));
        return this;
    }

    public ItemProperties tabIcon(RegistryObject<CreativeModeTab> tab) {
//        ((IItemPropertiesTab) properties).sinodivination$addTabAsIcon(TabsRegistry.items(tab));
//        sinodivination$addTabAsIcon.get().accept(properties, TabsRegistry.items(tab));
        MAP.computeIfAbsent(properties, p -> Pair.of(new ArrayList<>(), new ArrayList<>())).getSecond().add(TabsRegistry.items(tab));
        return this;
    }

    public ItemProperties tabIcon(ResourceKey<CreativeModeTab> tab) {
//        ((IItemPropertiesTab) properties).sinodivination$addTabAsIcon(TabsRegistry.items(tab));
//        sinodivination$addTabAsIcon.get().accept(properties, TabsRegistry.items(tab));
        MAP.computeIfAbsent(properties, p -> Pair.of(new ArrayList<>(), new ArrayList<>())).getSecond().add(TabsRegistry.items(tab));
        return this;
    }

    // Item.Property ===================================================================================================

    public ItemProperties food(FoodProperties food) {
        properties.food(food);
        return this;
    }

    public ItemProperties stacksTo(int maxStackSize) {
        properties.stacksTo(maxStackSize);
        return this;
    }

    public ItemProperties defaultDurability(int maxDamage) {
        properties.defaultDurability(maxDamage);
        return this;
    }

    public ItemProperties durability(int maxDamage) {
        properties.durability(maxDamage);
        return this;
    }

    public ItemProperties craftRemainder(Item craftingRemainingItem) {
        properties.craftRemainder(craftingRemainingItem);
        return this;
    }

    public ItemProperties rarity(Rarity rarity) {
        properties.rarity(rarity);
        return this;
    }

    public ItemProperties fireResistant() {
        properties.fireResistant();
        return this;
    }

    public ItemProperties setNoRepair() {
        properties.setNoRepair();
        return this;
    }

    public ItemProperties requiredFeatures(FeatureFlag... requiredFeatures) {
        properties.requiredFeatures(requiredFeatures);
        return this;
    }
}
