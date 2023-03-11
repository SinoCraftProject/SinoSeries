package games.moegirl.sinocraft.sinocore.old.woodwork;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class Woodwork {

    public static WoodworkBuilder builder(String name) {
        return new WoodworkBuilder(name);
    }

    // properties
    public final MaterialColor plankColor;
    public final WoodType type;
    public final ResourceLocation name;
    public final CreativeModeTab tab;

    // blocks
    public final RegistryObject<Block> planks;
    public final RegistryObject<SignBlock> sign;
    public final RegistryObject<SignBlock> wallSign;
    public final RegistryObject<PressurePlateBlock> pressurePlate;
    public final RegistryObject<TrapDoorBlock> trapdoor;
    public final RegistryObject<StairBlock> stairs;
    public final RegistryObject<ButtonBlock> button;
    public final RegistryObject<SlabBlock> slab;
    public final RegistryObject<FenceGateBlock> fenceGate;
    public final RegistryObject<FenceBlock> fence;
    public final RegistryObject<DoorBlock> door;
    private final Set<Block> allBlocks = new HashSet<>();

    // block entities
    @Nullable
    public final RegistryObject<BlockEntityType<ModSignBlockEntity>> signEntity;
    @Nullable
    public final RegistryObject<BlockEntityType<ModSignBlockEntity>> wallSignEntity;

    // items
    private final Set<Item> allItems = new HashSet<>();
    private final WoodworkRegisterHelper register = new WoodworkRegisterHelper(this);

    private final WoodworkManager manager;

    Woodwork(WoodworkBuilder builder, WoodworkManager manager) {
        this.manager = manager;
        this.plankColor = builder.plankColor;
        this.name = new ResourceLocation(manager.modid(), builder.name);
        this.type = WoodType.register(WoodType.create(name.toString()));
        this.tab = builder.tab;

        this.planks = addBlock(builder, builder.planks);
        this.sign = addBlock(builder, builder.sign);
        this.wallSign = addBlock(builder, builder.wallSign, false);
        this.pressurePlate = addBlock(builder, builder.pressurePlate);
        this.trapdoor = addBlock(builder, builder.trapdoor);
        this.stairs = addBlock(builder, builder.stairs);
        this.button = addBlock(builder, builder.button);
        this.slab = addBlock(builder, builder.slab);
        this.fenceGate = addBlock(builder, builder.fenceGate);
        this.fence = addBlock(builder, builder.fence);
        this.door = addBlock(builder, builder.door);

        this.signEntity = addBlockEntity(builder.sign, () -> new BlockEntityType<>((p, s) ->
                new ModSignBlockEntity(signEntity(), p, s), Set.of(sign()), null));
        this.wallSignEntity = addBlockEntity(builder.wallSign, () -> new BlockEntityType<>((p, s) ->
                new ModSignBlockEntity(wallSignEntity(), p, s), Set.of(wallSign()), null));

        manager.byId.put(name, this);
        manager.byType.put(type, this);
    }

    public ResourceLocation name() {
        return name;
    }

    public Block planks() {
        return planks.get();
    }

    public SignBlock sign() {
        return sign.get();
    }

    public SignBlock wallSign() {
        return wallSign.get();
    }

    public PressurePlateBlock pressurePlate() {
        return pressurePlate.get();
    }

    public TrapDoorBlock trapdoor() {
        return trapdoor.get();
    }

    public StairBlock stairs() {
        return stairs.get();
    }

    public ButtonBlock button() {
        return button.get();
    }

    public SlabBlock slab() {
        return slab.get();
    }

    public FenceGateBlock fenceGate() {
        return fenceGate.get();
    }

    public FenceBlock fence() {
        return fence.get();
    }

    public DoorBlock door() {
        return door.get();
    }

    public BlockEntityType<ModSignBlockEntity> signEntity() {
        return Objects.requireNonNull(signEntity).get();
    }

    public BlockEntityType<ModSignBlockEntity> wallSignEntity() {
        return Objects.requireNonNull(wallSignEntity).get();
    }

    public boolean useDefaultSignEntity() {
        return signEntity != null;
    }

    public boolean useDefaultWallSignEntity() {
        return wallSignEntity != null;
    }

    public Set<Block> allBlocks() {
        return Set.copyOf(allBlocks);
    }

    public Set<Item> allItems() {
        return Set.copyOf(allItems);
    }

    public WoodworkRegisterHelper register() {
        return register;
    }

    public WoodworkManager manager() {
        return manager;
    }

    private <B extends Block> RegistryObject<B> addBlock(WoodworkBuilder builder, BlockFactory<B, ? extends BlockItem, ?> factory, boolean hasItem) {
        RegistryObject<B> block = register(manager.blocks(), factory.name, () -> {
            B b = factory.newBlock(this, builder.strengthModifier);
            allBlocks.add(b);
            return b;
        });
        if (hasItem) {
            register(manager.items(), block, () -> {
                BlockItem i = factory.newItem(this);
                allItems.add(i);
                return i;
            });
        }
        return block;
    }

    private <B extends Block> RegistryObject<B> addBlock(WoodworkBuilder builder, BlockFactory<B, ? extends BlockItem, ?> factory) {
        return addBlock(builder, factory, true);
    }

    @Nullable
    private <T extends BlockEntity> RegistryObject<BlockEntityType<T>> addBlockEntity(BlockFactory<?, ?, ?> factory, Supplier<BlockEntityType<T>> sup) {
        return (factory.noBlock || factory.customEntity) ? null : register(manager.blockEntities(), factory.name, sup);
    }

    private <T> void register(DeferredRegister<? super T> register, RegistryObject<?> name, Supplier<T> supplier) {
        register.register(name.getId().getPath(), supplier);
    }

    private <T> RegistryObject<T> register(DeferredRegister<? super T> register, String postfix, Supplier<T> supplier) {
        return register.register(name.getPath() + "_" + postfix, supplier);
    }
}
