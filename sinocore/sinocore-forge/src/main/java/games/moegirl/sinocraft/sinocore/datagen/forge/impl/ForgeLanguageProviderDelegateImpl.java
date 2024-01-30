package games.moegirl.sinocraft.sinocore.datagen.forge.impl;

import games.moegirl.sinocraft.sinocore.datagen.delegate.LanguageProviderDelegateBase;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ForgeLanguageProviderDelegateImpl extends LanguageProviderDelegateBase {

    private final ForgeLanguageProviderImpl provider;

    public ForgeLanguageProviderDelegateImpl(PackOutput output, String modId, String locale) {
        super(new ForgeLanguageProviderImpl(output, modId, locale));
        provider = getForgeProvider();
        provider.setDelegate(this);
    }

    @Override
    public void addBlock(Supplier<? extends Block> key, String name) {
        provider.addBlock(key, name);
    }

    @Override
    public void addBlock(Block key, String name) {
        provider.add(key, name);
    }

    @Override
    public void addItem(Supplier<? extends Item> key, String name) {
        provider.addItem(key, name);
    }

    @Override
    public void addItem(Item key, String name) {
        provider.add(key, name);
    }

    @Override
    public void addItemStack(Supplier<ItemStack> key, String name) {
        provider.addItemStack(key, name);
    }

    @Override
    public void addItemStack(ItemStack key, String name) {
        provider.add(key, name);
    }

    @Override
    public void addEnchantment(Supplier<? extends Enchantment> key, String name) {
        provider.addEnchantment(key, name);
    }

    @Override
    public void addEnchantment(Enchantment key, String name) {
        provider.add(key, name);
    }

    @Override
    public void addEffect(Supplier<? extends MobEffect> key, String name) {
        provider.addEffect(key, name);
    }

    @Override
    public void addEffect(MobEffect key, String name) {
        provider.add(key, name);
    }

    @Override
    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        provider.addEntityType(key, name);
    }

    @Override
    public void addEntityType(EntityType<?> key, String name) {
        provider.add(key, name);
    }

    @Override
    public void addTab(ResourceKey<CreativeModeTab> key, String name) {
        CreativeModeTab group = BuiltInRegistries.CREATIVE_MODE_TAB.get(key);
        // 检查是否已经注册
        if (group != null) {
            if (group.getDisplayName().getContents() instanceof TranslatableContents lang) {
                add(lang.getKey(), name);
            }
            // 若对应 Tab 不需要语言文件，直接跳过
            getLogger().warn("Skipped add language to a tab without translatable name: {}", name);
        } else {
            // Tab 并未注册，只添加
            String lang = ITabRegistry.buildDefaultTranslationKey(key);
            getLogger().warn("Add language to unregistered tab {}: {} = {}.", key.location(), lang, name);
            add(lang, name);
        }
    }

    @Override
    public void addStatType(StatType<?> key, String value) {
        ResourceLocation statTypeKey = BuiltInRegistries.STAT_TYPE.getKey(key);
        if (statTypeKey == null) {
            getLogger().warn("Skipped add language to unregistered stat type: {}.", value);
        } else {
            add("stat_type." + statTypeKey.toString().replace(':', '.'), value);
        }
    }

    @Override
    public void add(String key, String value) {
        provider.add(key, value);
    }
}
