package games.moegirl.sinocraft.sinocore.tree;

import com.mojang.datafixers.util.Function3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author luqin2007
 */
public class TreeBlockFactory {

    final TreeBlockType type;

    Function3<DeferredRegister<Block>, String, Tree, RegistryObject<? extends Block>> blockBuilder;

    Function<Tree, BlockBehaviour.Properties> blockPropBuilder;

    Function3<DeferredRegister<Item>, String, Tree, RegistryObject<? extends Item>> itemBuilder;

    final List<ResourceLocation> tabs = new ArrayList<>();

    final List<TagKey<Block>> blockTags = new ArrayList<>();

    final List<TagKey<Item>> itemTags = new ArrayList<>();

    boolean fillDefaultTabs = false;

    boolean addDefaultBlockTag = true, addDefaultItemTag = true;

    public TreeBlockFactory(TreeBlockType type) {
        this.type = type;
        blockBuilder = wrapBlock(type.defBlock);
        blockPropBuilder = type.defBlockProp;
        itemBuilder = wrapItem(type.defItem);
    }

    public List<TagKey<Block>> getBlockTags() {
        return blockTags;
    }

    public List<TagKey<Item>> getItemTags() {
        return itemTags;
    }

    public boolean isAddDefaultBlockTag() {
        return addDefaultBlockTag;
    }

    public boolean isAddDefaultItemTag() {
        return addDefaultItemTag;
    }

    Function3<DeferredRegister<Block>, String, Tree, RegistryObject<? extends Block>> wrapBlock(BiFunction<Tree, BlockBehaviour.Properties, Block> factory) {
        return (dr, name, tree) -> dr.register(name, () -> factory.apply(tree, blockPropBuilder.apply(tree)));
    }

    Function3<DeferredRegister<Item>, String, Tree, RegistryObject<? extends Item>> wrapItem(BiFunction<Tree, TreeBlockType, Item> factory) {
        return (dr, name, tree) -> dr.register(name, () -> factory.apply(tree, type));
    }
}
