package games.moegirl.sinocraft.sinocore.woodwork;

import games.moegirl.sinocraft.sinocore.mixin_inter.IBlockProperties;
import games.moegirl.sinocraft.sinocore.utility.FloatModifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author luqin2007
 */
public class BlockFactory<B extends Block, I extends BlockItem> {

    final WoodworkBuilder builder;
    String name;
    Function<Woodwork, BlockBehaviour.Properties> properties;
    @Nullable
    Function<Woodwork, Item.Properties> itemProperties;
    BiFunction<BlockBehaviour.Properties, Woodwork, B> factory;
    BiFunction<Item.Properties, Woodwork, I> itemFactory;
    boolean customEntity = false;
    boolean noBlock = false;
    List<CreativeModeTab> tabs;

    public BlockFactory(WoodworkBuilder builder, String name,
                        Function<Woodwork, BlockBehaviour.Properties> properties,
                        @Nullable
                        Function<Woodwork, Item.Properties> itemProperties,
                        BiFunction<BlockBehaviour.Properties, Woodwork, B> factory,
                        BiFunction<Item.Properties, Woodwork, I> itemFactory,
                        List<CreativeModeTab> tabs) {
        this.builder = builder;
        this.name = name;
        this.properties = properties;
        this.itemProperties = itemProperties;
        this.factory = factory;
        this.itemFactory = itemFactory;
        this.tabs = tabs;
    }

    public BlockFactory(WoodworkBuilder builder, String name,
                        Function<Woodwork, BlockBehaviour.Properties> properties,
                        BiFunction<BlockBehaviour.Properties, Woodwork, B> factory,
                        BiFunction<Item.Properties, Woodwork, I> itemFactory,
                        List<CreativeModeTab> tabs) {
        this(builder, name, properties, null, factory, itemFactory, tabs);
    }

    public BlockFactory(WoodworkBuilder builder, String name,
                        Function<Woodwork, BlockBehaviour.Properties> properties,
                        Function<BlockBehaviour.Properties, B> factory,
                        BiFunction<Item.Properties, Woodwork, I> itemFactory,
                        List<CreativeModeTab> tabs) {
        this(builder, name, properties, null, (p, b) -> factory.apply(p), itemFactory, tabs);
    }

    public B newBlock(Woodwork woodwork, FloatModifier strengthModifier) {
        BlockBehaviour.Properties prop = properties.apply(woodwork);
        float strength = ((IBlockProperties) prop).getDestroyTime();
        float resistance = ((IBlockProperties) prop).getExplosionResistance();
        prop = prop.strength(strengthModifier.apply(strength), resistance);
        return factory.apply(prop, woodwork);
    }

    public I newItem(Woodwork woodwork) {
        return itemFactory.apply(itemProperties == null
                ? builder.defaultItemProperties.apply(woodwork)
                : itemProperties.apply(woodwork), woodwork);
    }
}
