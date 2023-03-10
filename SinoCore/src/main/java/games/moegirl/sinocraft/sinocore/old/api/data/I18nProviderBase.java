package games.moegirl.sinocraft.sinocore.old.api.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Supplier;

public abstract class I18nProviderBase implements DataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    protected final Map<String, String> data = new TreeMap<>();

    protected final DataGenerator gen;
    protected final String modId;
    protected final String mainModId;
    protected final String locale;

    public I18nProviderBase(DataGenerator genIn, String modIdIn, String mainModIdIn, String localeIn) {
        gen = genIn;
        modId = modIdIn;
        mainModId = mainModIdIn;
        locale = localeIn;
    }

    protected abstract void addTranslations();

    @Override
    public String getName() {
        return "Mod " + modId + " languages " + locale;
    }

    public void add(String key, String value) {
        if (data.put(key, value) != null) {
            throw new IllegalStateException("Duplicate translation key " + key);
        }
    }

    @Override
    public void run(HashCache cache) throws IOException {
        addTranslations();
        if (!data.isEmpty()) {
            save(cache, data, getPath());
        }
    }

    protected void save(HashCache cache, Object object, Path target) throws IOException {
        String data = GSON.toJson(object);
        // Escape unicode after the fact so that it's not double escaped by GSON
        //noinspection deprecation
        data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data);
        String hash = DataProvider.SHA1.hashUnencodedChars(data).toString();
        if (!Objects.equals(cache.getHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
                bufferedwriter.write(data);
            }
        }

        cache.putNew(target, hash);
    }

    protected Path getPath() {
        return gen.getOutputFolder().resolve("assets/" + mainModId + "/lang/" + locale + ".json");
    }

    // Add gen helper below this line.
    
    public void addBlock(Supplier<? extends Block> key, String name) {
        add(key.get(), name);
    }

    public void add(Block key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addItem(Supplier<? extends Item> key, String name) {
        add(key.get(), name);
    }

    public void add(Item key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addItemStack(Supplier<ItemStack> key, String name) {
        add(key.get(), name);
    }

    public void add(ItemStack key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEnchantment(Supplier<? extends Enchantment> key, String name) {
        add(key.get(), name);
    }

    public void add(Enchantment key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEffect(Supplier<? extends MobEffect> key, String name) {
        add(key.get(), name);
    }

    public void add(MobEffect key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        add(key.get(), name);
    }

    public void add(EntityType<?> key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addCreativeTab(CreativeModeTab tab, String value) {
        add("itemGroup." + tab.langId, value);
    }
}
