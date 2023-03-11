package games.moegirl.sinocraft.sinocore.old.data.base;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Add english and chinese language
 */
public abstract class LanguageProviderBase implements DataProvider {

    protected final Map<String, String> enData = new HashMap<>();
    protected final Map<String, String> zhData = new HashMap<>();
    protected final Map<String, String> childEnData = new HashMap<>();
    protected final Map<String, String> childZhData = new HashMap<>();

    protected final LanguageProvider en, zh;

    protected final PackOutput output;
    protected final String modid;

    private boolean added = false;

    public LanguageProviderBase(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;

        this.en = new SubProvider(output, modid, true);
        this.zh = new SubProvider(output, modid, false);
    }

    /**
     * Add all translations
     */
    protected abstract void addTranslations();

    /**
     * Add block name
     * @param key block
     * @param en english name
     * @param zh chinese name
     */
    public void addBlock(Supplier<? extends Block> key, String en, String zh) {
        add(key.get(), en, zh);
    }

    /**
     * Add block name
     * @param key block
     * @param en english name
     * @param zh chinese name
     */
    public void add(Block key, String en, String zh) {
        add(key.getDescriptionId(), en, zh);
    }

    /**
     * Add item name
     * @param key item
     * @param en english name
     * @param zh chinese name
     */
    public void addItem(Supplier<? extends Item> key, String en, String zh) {
        add(key.get(), en, zh);
    }

    /**
     * Add item name
     * @param key item
     * @param en english name
     * @param zh chinese name
     */
    public void add(Item key, String en, String zh) {
        add(key.getDescriptionId(), en, zh);
    }

    /**
     * Add item name
     * @param key item
     * @param en english name
     * @param zh chinese name
     */
    public void addItemStack(Supplier<ItemStack> key, String en, String zh) {
        add(key.get(), en, zh);
    }

    /**
     * Add item name
     * @param key item
     * @param en english name
     * @param zh chinese name
     */
    public void add(ItemStack key, String en, String zh) {
        add(key.getDescriptionId(), en, zh);
    }

    /**
     * Add enchantment name
     * @param key enchantment
     * @param en english name
     * @param zh chinese name
     */
    public void addEnchantment(Supplier<? extends Enchantment> key, String en, String zh) {
        add(key.get(), en, zh);
    }

    /**
     * Add enchantment name
     * @param key enchantment
     * @param en english name
     * @param zh chinese name
     */
    public void add(Enchantment key, String en, String zh) {
        add(key.getDescriptionId(), en, zh);
    }

    /**
     * Add potion effect name
     * @param key potion effect
     * @param en english name
     * @param zh chinese name
     */
    public void addEffect(Supplier<? extends MobEffect> key, String en, String zh) {
        add(key.get(), en, zh);
    }

    /**
     * Add potion effect name
     * @param key potion effect
     * @param en english name
     * @param zh chinese name
     */
    public void add(MobEffect key, String en, String zh) {
        add(key.getDescriptionId(), en, zh);
    }

    /**
     * Add entity name
     * @param key potion effect
     * @param en english name
     * @param zh chinese name
     */
    public void addEntityType(Supplier<? extends EntityType<?>> key, String en, String zh) {
        add(key.get(), en, zh);
    }

    /**
     * Add entity name
     * @param key potion effect
     * @param en english name
     * @param zh chinese name
     */
    public void add(EntityType<?> key, String en, String zh) {
        add(key.getDescriptionId(), en, zh);
    }

    /**
     * Add creative mode tab name
     * @param key tab
     * @param en english name
     * @param zh chinese name
     */
    public void addGroup(CreativeModeTab key, String en, String zh) {
        addKey(key.getDisplayName(), en, zh);
    }

    /**
     * Add language id name
     * @param key key
     * @param en english name
     * @param zh chinese name
     */
    public void addKey(Component key, String en, String zh) {
        if (key instanceof MutableComponent mc && mc.getContents() instanceof TranslatableContents tc) {
            add(tc.getKey(), en, zh);
        }
    }

    /**
     * Add custom key
     * @param key key
     * @param en english name
     * @param zh chinese name
     */
    public void add(String key, String en, String zh) {
        if (enData.put(key, en) != null)
            throw new IllegalStateException("Duplicate en_us translation key " + key);
        if (zhData.put(key, zh) != null)
            throw new IllegalStateException("Duplicate zh_cn translation key " + key);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.allOf(en.run(output), zh.run(output));
    }

    @Override
    public String getName() {
        return "Languages: en_us & zh_cn";
    }

    void addTranslationsToProvider(boolean en) {
        if (!added) {
            addTranslations();
            added = true;
        }

        Map<String, String> data = en ? enData : zhData;
        LanguageProvider provider = en ? this.en : this.zh;

        data.forEach(provider::add);
    }

    protected class SubProvider extends LanguageProvider {

        private final boolean en;

        public SubProvider(PackOutput output, String modid, boolean en) {
            super(output, modid, en ? "en_us" : "zh_cn");
            this.en = en;
        }

        @Override
        protected void addTranslations() {
            LanguageProviderBase.this.addTranslationsToProvider(en);
        }

        @Override
        public void add(String key, String value) {
            super.add(key, value);
            Map<String, String> map = en ? childEnData : childZhData;
            map.put(key, value);
        }
    }
}
