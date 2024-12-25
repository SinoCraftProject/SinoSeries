package games.moegirl.sinocraft.sinocore.data.gen.delegate;

import games.moegirl.sinocraft.sinocore.data.gen.model.IItemModelBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelProvider;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public abstract class ItemModelProviderDelegateBase<T extends IItemModelBuilder<T>> 
        extends ProviderDelegateBase<ItemModelProviderDelegateBase<T>> implements IModelProvider<T> {

    public static final ResourceLocation GENERATED = ResourceLocation.withDefaultNamespace("item/generated");
    public static final ResourceLocation HANDHELD = ResourceLocation.withDefaultNamespace("item/handheld");

    protected ItemModelProviderDelegateBase(DataProvider provider) {
        super(provider);
    }

    public abstract void genDefaultItemModel(Item item);

    public abstract T getBuilder(String path);

    public abstract void skipItem(Item... items);

    public abstract void printExceptions();

    public abstract T basicItem(Item item);

    public abstract T basicItem(ResourceLocation item);

    public abstract T withBlockParent(IRegRef<Block> block);

    public abstract void generated(ItemLike item);

    public abstract void handheld(ItemLike item);

    public abstract void blockItem(Block block);

    public abstract void blockItem(Block block, String statedModel);
}
