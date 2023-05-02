package games.moegirl.sinocraft.sinocore.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractItemModelProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * {@code ItemModelProviderBase} <br />
 * {@link AbstractItemModelProvider} with deferred registers auto register.
 *
 * @author skyinr, qyl27
 */
public abstract class ItemModelProviderBase extends AbstractItemModelProvider {
    private final Set<Item> skipItems = new HashSet<>();
    private final Set<String> addedItems = new HashSet<>();
    private boolean adding = false;

    private final DeferredRegister<? extends Item>[] deferredRegisters;

    @SafeVarargs
    public ItemModelProviderBase(PackOutput output, String modId, ExistingFileHelper exHelper, DeferredRegister<? extends Item>... deferredRegisters) {
        super(output, modId, exHelper);
        this.deferredRegisters = deferredRegisters;
        // qyl: We need not an additional modId variable. MC already have.
    }

    protected abstract void registerItemModels();

    /**
     * Register item model.
     */
    @Override
    protected final void registerModels() {
        setAdding(true);
        registerItemModels();
        setAdding(false);

        getItems().forEach(this::genItemModel);
    }

    protected Set<Item> getItems() {
        return Arrays.stream(deferredRegisters)
                .flatMap(dr -> dr.getEntries().stream())
                .map(RegistryObject::get)
//                .filter(i -> !(i instanceof CrossbowItem))
                .filter(i -> !skipItems.contains(i))
                .filter(i -> !addedItems.contains(name(i)))
                .collect(Collectors.toSet());
    }

    protected void genItemModel(Item item) {
        // Todo: more generated model.
        if (item instanceof BlockItem i) {
            ResourceLocation texture = modLoc("item/" + name(item));
            if (existingFileHelper.exists(texture, TEXTURE)) {
                generated(item);
            } else {
                withExistingParent(name(i), modLoc("block/" + name(i)));
            }
        } else if (item instanceof TieredItem) {
            handheld(item);
        } else {
            generated(item);
        }
    }

    /**
     * get item name
     *
     * @param item item
     * @return item name
     */
    protected static String name(Item item) { // Todo: qyl: Avoid direct use string Path, use ResourceLocation instead.
        //noinspection DataFlowIssue
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    @Override
    public ItemModelBuilder getBuilder(String path) {
        if (isAdding()) {
            addedItem(path);
        }
        return super.getBuilder(path);
    }

    @Override
    protected CompletableFuture<?> generateAll(CachedOutput cache) {
        var futures = generatedModels.values().stream()
                .map(model -> {
                    Path target = getPath(model);
                    return DataProvider.saveStable(cache, model.toJson(), target);
                })
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }

    /**
     * Generate all path.
     */
    public Path getPath(ItemModelBuilder model) {
        ResourceLocation loc = model.getLocation();
        return output.getOutputFolder().resolve("assets/"
                + modid + "/models/" + loc.getPath() + ".json");    // qyl: No longer use modid of deferred register.
    }

    protected boolean isAdding() {
        return adding;
    }

    private void setAdding(boolean adding) {
        this.adding = adding;
    }

    protected void skipItem(Item... items) {
        skipItems.addAll(Arrays.asList(items));
    }

    private void addedItem(String... items) {
        addedItems.addAll(Arrays.asList(items));
    }
}
