package games.moegirl.sinocraft.sinocore.data.gen.delegate;

import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public abstract class LanguageProviderDelegateBase extends ProviderDelegateBase<LanguageProviderDelegateBase> {

    protected LanguageProviderDelegateBase(DataProvider provider) {
        super(provider);
    }

    public abstract void addBlock(Supplier<? extends Block> key, String name);

    public abstract void addBlock(Block key, String name);

    public abstract void addItem(Supplier<? extends Item> key, String name);

    public abstract void addItem(Item key, String name);

    public abstract void addItemStack(Supplier<ItemStack> key, String name);

    public abstract void addItemStack(ItemStack key, String name);

    public abstract void addEffect(Supplier<? extends MobEffect> key, String name);

    public abstract void addEffect(MobEffect key, String name);

    public abstract void addEntityType(Supplier<? extends EntityType<?>> key, String name);

    public abstract void addEntityType(EntityType<?> key, String name);

    public abstract void addTab(ResourceKey<CreativeModeTab> key, String name);

    public abstract void addStatType(StatType<?> key, String value);

    public abstract void addCustomStat(ResourceLocation key, String value);

    public abstract void add(String key, String value);

    public void addItemTag(TagKey<Item> tag, String value) {
        add("tag.item." + tag.location().getNamespace() + "." + tag.location().getPath(), value);
    }
}
