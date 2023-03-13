package games.moegirl.sinocraft.sinocore.woodwork;

import games.moegirl.sinocraft.sinocore.utility.FloatModifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * If you use custom block, and need custom drop loot table, you should
 * implement {@link games.moegirl.sinocraft.sinocore.api.block.ILootableBlock} interface to the custom block.
 */
public class WoodworkBuilder {

    final String name;
    CreativeModeTab tab = CreativeModeTabs.BUILDING_BLOCKS;
    MaterialColor plankColor = MaterialColor.WOOD;

    Function<Woodwork, Item.Properties> defaultItemProperties = w -> new Item.Properties()/*todo .tab(w.tab)*/;

    BlockFactory<Block, BlockItem, ?> planks = new BlockFactory<>(this, "planks",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.plankColor)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD),
            Block::new,
            defaultBlockItem(Woodwork::planks));

    BlockFactory<SignBlock, BlockItem, ?> sign = new BlockFactory<>(this, "sign",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.plankColor)
                    .noCollission()
                    .strength(1.0F)
                    .sound(SoundType.WOOD),
            w -> new Item.Properties().stacksTo(16)/*todo .tab(w.tab)*/,
            ModSignBlockStanding::new,
            ModSignItem::new);
    BlockFactory<SignBlock, BlockItem, ?> wallSign = new BlockFactory<>(this, "wall_sign",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.plankColor)
                    .noCollission()
                    .strength(1.0F)
                    .sound(SoundType.WOOD)
                    .lootFrom(w.sign),
            ModSignBlockWall::new,
            ModSignItem::new);

    BlockFactory<PressurePlateBlock, BlockItem, ?> pressurePlate = new BlockFactory<>(this, "pressure_plate",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.planks().defaultMaterialColor())
                    .noCollission()
                    .strength(0.5F)
                    .sound(SoundType.WOOD),
            (p, w) -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, p, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON),
            defaultBlockItem(Woodwork::pressurePlate));

    BlockFactory<TrapDoorBlock, BlockItem, ?> trapdoor = new BlockFactory<>(this, "trapdoor",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD/*, w.plankColor*/)
                    .strength(3.0F)
                    .sound(SoundType.WOOD)
                    .noOcclusion()
                    .isValidSpawn((_1, _2, _3, _4) -> false),
            (p, w) -> new TrapDoorBlock(p, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN),
            defaultBlockItem(Woodwork::trapdoor));

    BlockFactory<StairBlock, BlockItem, ?> stairs = new BlockFactory<>(this, "stairs",
            w -> BlockBehaviour.Properties.copy(w.planks()),
            (p, w) -> new StairBlock(() -> w.planks().defaultBlockState(), p),
            defaultBlockItem(Woodwork::stairs));

    BlockFactory<ButtonBlock, BlockItem, ?> button = new BlockFactory<>(this, "button",
            w -> BlockBehaviour.Properties
                    .of(Material.DECORATION)
                    .noCollission()
                    .strength(0.5F)
                    .sound(SoundType.WOOD),
            (p, w) -> new ButtonBlock(p, 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON),
            defaultBlockItem(Woodwork::button));

    BlockFactory<SlabBlock, BlockItem, ?> slab = new BlockFactory<>(this, "slab",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.plankColor)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD),
            SlabBlock::new,
            defaultBlockItem(Woodwork::slab));

    BlockFactory<FenceGateBlock, BlockItem, ?> fenceGate = new BlockFactory<>(this, "fence_gate",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.planks().defaultMaterialColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD),
            (p, w) -> new FenceGateBlock(p, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN),
            defaultBlockItem(Woodwork::fenceGate));

    BlockFactory<FenceBlock, BlockItem, ?> fence = new BlockFactory<>(this, "fence",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.planks().defaultMaterialColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD),
            FenceBlock::new,
            defaultBlockItem(Woodwork::fence));

    BlockFactory<DoorBlock, BlockItem, ?> door = new BlockFactory<>(this, "door",
            w -> BlockBehaviour.Properties
                    .of(Material.WOOD, w.planks().defaultMaterialColor())
                    .strength(3.0F)
                    .sound(SoundType.WOOD)
                    .noOcclusion(),
            (p, w) -> new DoorBlock(p, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN),
            (p, w) -> new DoubleHighBlockItem(w.door(), p));

    FloatModifier strengthModifier = new FloatModifier();

    public WoodworkBuilder(String name) {
        this.name = name;
    }

    public WoodworkBuilder defaultTab(CreativeModeTab tab) {
        this.tab = tab;
        return this;
    }

    public WoodworkBuilder plankColor(MaterialColor color) {
        this.plankColor = color;
        return this;
    }

    public WoodworkBuilder defaultItemProperties(Function<Woodwork, Item.Properties> factory) {
        this.defaultItemProperties = factory;
        return this;
    }

    public WoodworkBuilder customPlanks(Function<Woodwork, Block> factory) {
        return customBlock(planks, factory);
    }

    public WoodworkBuilder customPlanks(BiFunction<BlockBehaviour.Properties, Woodwork, Block> factory) {
        return customBlock(planks, factory);
    }

    public WoodworkBuilder customPlanksItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(planks, factory);
    }

    public WoodworkBuilder customPlanksItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(planks, factory);
    }

    public WoodworkBuilder customPlanksProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(planks, factory);
    }

    public WoodworkBuilder customPlanksItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(planks, factory);
    }

    public WoodworkBuilder customSign(Function<Woodwork, SignBlock> factory) {
        return customBlock(sign, factory);
    }

    public WoodworkBuilder customSign(Function<Woodwork, SignBlock> factory, boolean customEntity) {
        return customBlock(sign, customEntity, factory);
    }

    public WoodworkBuilder customSign(BiFunction<BlockBehaviour.Properties, Woodwork, SignBlock> factory) {
        return customBlock(sign, factory);
    }

    public WoodworkBuilder customSign(BiFunction<BlockBehaviour.Properties, Woodwork, SignBlock> factory, boolean customEntity) {
        return customBlock(sign, customEntity, factory);
    }
    
    public WoodworkBuilder customSignEntity(boolean customEntity) {
        return customBlockEntity(sign, customEntity);
    }

    public WoodworkBuilder customSignItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(sign, factory);
    }

    public WoodworkBuilder customSignItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(sign, factory);
    }

    public WoodworkBuilder customSignProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(sign, factory);
    }

    public WoodworkBuilder customSignItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(sign, factory);
    }

    public WoodworkBuilder customWallSign(Function<Woodwork, SignBlock> factory) {
        return customBlock(wallSign, factory);
    }

    public WoodworkBuilder customWallSign(Function<Woodwork, SignBlock> factory, boolean customEntity) {
        return customBlock(wallSign, customEntity, factory);
    }

    public WoodworkBuilder customWallSign(BiFunction<BlockBehaviour.Properties, Woodwork, SignBlock> factory) {
        return customBlock(wallSign, factory);
    }

    public WoodworkBuilder customWallSign(BiFunction<BlockBehaviour.Properties, Woodwork, SignBlock> factory, boolean customEntity) {
        return customBlock(wallSign, customEntity, factory);
    }

    public WoodworkBuilder customWallSignProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(wallSign, factory);
    }

    public WoodworkBuilder customWallSignEntity(boolean customEntity) {
        return customBlockEntity(wallSign, customEntity);
    }

    public WoodworkBuilder customPressurePlate(Function<Woodwork, PressurePlateBlock> factory) {
        return customBlock(pressurePlate, factory);
    }

    public WoodworkBuilder customPressurePlate(BiFunction<BlockBehaviour.Properties, Woodwork, PressurePlateBlock> factory) {
        return customBlock(pressurePlate, factory);
    }

    public WoodworkBuilder customPressurePlateItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(pressurePlate, factory);
    }

    public WoodworkBuilder customPressurePlateItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(pressurePlate, factory);
    }

    public WoodworkBuilder customPressurePlateProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(pressurePlate, factory);
    }

    public WoodworkBuilder customPressurePlateItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(pressurePlate, factory);
    }

    public WoodworkBuilder customTrapdoor(Function<Woodwork, TrapDoorBlock> factory) {
        return customBlock(trapdoor, factory);
    }

    public WoodworkBuilder customTrapdoor(BiFunction<BlockBehaviour.Properties, Woodwork, TrapDoorBlock> factory) {
        return customBlock(trapdoor, factory);
    }

    public WoodworkBuilder customTrapdoorItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(trapdoor, factory);
    }

    public WoodworkBuilder customTrapdoorItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(trapdoor, factory);
    }

    public WoodworkBuilder customTrapdoorProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(trapdoor, factory);
    }

    public WoodworkBuilder customTrapdoorItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(trapdoor, factory);
    }

    public WoodworkBuilder customStairs(Function<Woodwork, StairBlock> factory) {
        return customBlock(stairs, factory);
    }

    public WoodworkBuilder customStairs(BiFunction<BlockBehaviour.Properties, Woodwork, StairBlock> factory) {
        return customBlock(stairs, factory);
    }

    public WoodworkBuilder customStairsItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(stairs, factory);
    }

    public WoodworkBuilder customStairsItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(stairs, factory);
    }

    public WoodworkBuilder customStairsProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(stairs, factory);
    }

    public WoodworkBuilder customStairsItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(stairs, factory);
    }

    public WoodworkBuilder customButton(Function<Woodwork, ButtonBlock> factory) {
        return customBlock(button, factory);
    }

    public WoodworkBuilder customButton(BiFunction<BlockBehaviour.Properties, Woodwork, ButtonBlock> factory) {
        return customBlock(button, factory);
    }

    public WoodworkBuilder customButtonItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(button, factory);
    }

    public WoodworkBuilder customButtonItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(button, factory);
    }

    public WoodworkBuilder customButtonProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(button, factory);
    }

    public WoodworkBuilder customButtonItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(button, factory);
    }

    public WoodworkBuilder customSlab(Function<Woodwork, SlabBlock> factory) {
        return customBlock(slab, factory);
    }

    public WoodworkBuilder customSlab(BiFunction<BlockBehaviour.Properties, Woodwork, SlabBlock> factory) {
        return customBlock(slab, factory);
    }

    public WoodworkBuilder customSlabItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(slab, factory);
    }

    public WoodworkBuilder customSlabItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(slab, factory);
    }

    public WoodworkBuilder customSlabProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(slab, factory);
    }

    public WoodworkBuilder customSlabItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(slab, factory);
    }

    public WoodworkBuilder customFence(Function<Woodwork, FenceBlock> factory) {
        return customBlock(fence, factory);
    }

    public WoodworkBuilder customFence(BiFunction<BlockBehaviour.Properties, Woodwork, FenceBlock> factory) {
        return customBlock(fence, factory);
    }

    public WoodworkBuilder customFenceItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(fence, factory);
    }

    public WoodworkBuilder customFenceItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(fence, factory);
    }

    public WoodworkBuilder customFenceProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(fence, factory);
    }

    public WoodworkBuilder customFenceItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(fence, factory);
    }

    public WoodworkBuilder customFenceGate(Function<Woodwork, FenceGateBlock> factory) {
        return customBlock(fenceGate, factory);
    }

    public WoodworkBuilder customFenceGate(BiFunction<BlockBehaviour.Properties, Woodwork, FenceGateBlock> factory) {
        return customBlock(fenceGate, factory);
    }

    public WoodworkBuilder customFenceGateItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(fenceGate, factory);
    }

    public WoodworkBuilder customFenceGateItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(fenceGate, factory);
    }

    public WoodworkBuilder customFenceGateProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(fenceGate, factory);
    }

    public WoodworkBuilder customFenceGateItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(fenceGate, factory);
    }

    public WoodworkBuilder customDoor(Function<Woodwork, DoorBlock> factory) {
        return customBlock(door, factory);
    }

    public WoodworkBuilder customDoor(BiFunction<BlockBehaviour.Properties, Woodwork, DoorBlock> factory) {
        return customBlock(door, factory);
    }

    public WoodworkBuilder customDoorItem(Function<Woodwork, BlockItem> factory) {
        return customBlockItem(door, factory);
    }

    public WoodworkBuilder customDoorItem(BiFunction<Item.Properties, Woodwork, BlockItem> factory) {
        return customBlockItem(door, factory);
    }

    public WoodworkBuilder customDoorProperties(Function<Woodwork, BlockBehaviour.Properties> factory) {
        return customProperties(door, factory);
    }

    public WoodworkBuilder customDoorItemProperties(Function<Woodwork, Item.Properties> factory) {
        return customItemProperties(door, factory);
    }

    public WoodworkBuilder customStrengthModifier(Function<FloatModifier, FloatModifier> modifier) {
        strengthModifier = modifier.apply(strengthModifier);
        return this;
    }

    private <T extends Block> WoodworkBuilder customBlock(BlockFactory<T, ?, ?> holder, boolean customEntity, Function<Woodwork, T> factory) {
        holder.factory = (p, w) -> factory.apply(w);
        holder.customEntity = customEntity;
        return this;
    }

    private <T extends Block> WoodworkBuilder customBlock(BlockFactory<T, ?, ?> holder, Function<Woodwork, T> factory) {
        holder.factory = (p, w) -> factory.apply(w);
        return this;
    }

    private <T extends Block> WoodworkBuilder customBlock(BlockFactory<T, ?, ?> holder, boolean customEntity, BiFunction<BlockBehaviour.Properties, Woodwork, T> factory) {
        holder.factory = factory;
        holder.customEntity = customEntity;
        return this;
    }

    private <T extends Block> WoodworkBuilder customBlock(BlockFactory<T, ?, ?> holder, BiFunction<BlockBehaviour.Properties, Woodwork, T> factory) {
        holder.factory = factory;
        return this;
    }

    private <T extends BlockItem> WoodworkBuilder customBlockItem(BlockFactory<?, T, ?> holder, Function<Woodwork, T> factory) {
        holder.itemFactory = (p, w) -> factory.apply(w);
        return this;
    }

    private <T extends BlockItem> WoodworkBuilder customBlockItem(BlockFactory<?, T, ?> holder, BiFunction<Item.Properties, Woodwork, T> factory) {
        holder.itemFactory = factory;
        return this;
    }

    private WoodworkBuilder customProperties(BlockFactory<?, ?, ?> holder, Function<Woodwork, BlockBehaviour.Properties> factory) {
        holder.properties = factory;
        return this;
    }

    private WoodworkBuilder customItemProperties(BlockFactory<?, ?, ?> holder, Function<Woodwork, Item.Properties> factory) {
        holder.itemProperties = factory;
        return this;
    }

    private WoodworkBuilder customBlockEntity(BlockFactory<?, ?, ?> holder, boolean customEntity) {
        holder.customEntity = customEntity;
        return this;
    }

    private BiFunction<Item.Properties, Woodwork, BlockItem> defaultBlockItem(Function<Woodwork, Block> block) {
        return (prop, woodwork) -> new BlockItem(block.apply(woodwork), prop);
    }

    public Woodwork build(WoodworkManager manager) {
        return new Woodwork(this, manager);
    }
}
