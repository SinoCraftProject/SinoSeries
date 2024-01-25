package games.moegirl.sinocraft.sinocore.datagen.delegate;

import games.moegirl.sinocraft.sinocore.datagen.model.IItemModelBuilder;
import games.moegirl.sinocraft.sinocore.datagen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.datagen.model.IModelProvider;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public abstract class ItemModelProviderDelegateBase<T extends IItemModelBuilder<T>> 
        extends ProviderDelegateBase<ItemModelProviderDelegateBase<T>> implements IModelProvider<T> {

    protected ItemModelProviderDelegateBase(DataProvider provider) {
        super(provider);
    }

    public abstract void genDefaultItemModel(Item item);

    public abstract T getBuilder(String path);

    public abstract void skipItem(Item... items);

    public abstract IModelFile getExistingFile(ResourceLocation path);

    public abstract IModelFile weakCheckModel(ResourceLocation path);

    public abstract void printExceptions();

    public abstract ResourceLocation blockLoc(ResourceLocation path);

    public abstract ResourceLocation foldedLoc(ResourceLocation path);

    public abstract T basicItem(Item item);

    public abstract T basicItem(ResourceLocation item);

    public abstract T withBlockParent(Block block);

    public abstract T withBlockParent(IRegRef<Block, ?> block);

    public abstract void generated(ItemLike item);

    public abstract void handheld(ItemLike item);

    public abstract void handheld(Block block);

    public abstract void blockItem(Block block);

    public abstract void blockItem(Block block, String statedModel);

//    public abstract void chest(IRegRef<? extends Item, ?> chest, IRegRef<? extends Item, ?> trappedChest, Tree tree);
}
