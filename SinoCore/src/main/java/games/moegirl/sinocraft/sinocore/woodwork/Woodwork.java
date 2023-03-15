package games.moegirl.sinocraft.sinocore.woodwork;

import games.moegirl.sinocraft.sinocore.blockentity.ModSignBlockEntity;
import games.moegirl.sinocraft.sinocore.handler.WoodworkDataHandler;
import games.moegirl.sinocraft.sinocore.handler.WoodworkEventHandler;
import games.moegirl.sinocraft.sinocore.packet.SignEditOpenS2CPacket;
import games.moegirl.sinocraft.sinocore.packet.SignTextUpdateC2SPacket;
import games.moegirl.sinocraft.sinocore.utility.NetworkHolder;
import games.moegirl.sinocraft.sinocore.utility.RegType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 木制品，包括木板，告示牌，楼梯，半砖，按钮，压力板，门等
 * <p>使用时应在 mod 主类中使用 {@link Woodwork#register(String, IEventBus, NetworkHolder)} 注册</p>
 */
public class Woodwork {

    private static final HashMap<ResourceLocation, Woodwork> BY_ID = new HashMap<>();
    private static final HashMap<WoodType, Woodwork> BY_WOOD = new HashMap<>();
    private static final HashMap<String, NetworkHolder> NETS = new HashMap<>();

    public static boolean has(ResourceLocation name) {
        return BY_ID.containsKey(name);
    }

    public static Woodwork get(ResourceLocation name) {
        return BY_ID.get(name);
    }

    public static boolean has(WoodType type) {
        return BY_WOOD.containsKey(type);
    }

    public static Woodwork get(WoodType type) {
        return BY_WOOD.get(type);
    }

    public static NetworkHolder network(String modid) {
        return NETS.get(modid);
    }

    public static WoodworkBuilder builder(ResourceLocation name, String enName, String zhName) {
        return new WoodworkBuilder(name, enName, zhName);
    }

    public static WoodworkBuilder builder(ResourceLocation name, String zhName) {
        return new WoodworkBuilder(name, zhName, Arrays.stream(name.getPath().split("_"))
                .filter(s -> !s.isEmpty())
                .map(s -> s.length() == 1 ? s.toUpperCase(Locale.ROOT) : Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" ")));
    }

    public static void register(String modid, IEventBus bus, NetworkHolder network) {
        NETS.put(modid, network);
        network.register(SignEditOpenS2CPacket.class, SignEditOpenS2CPacket::write, SignEditOpenS2CPacket::read);
        network.register(SignTextUpdateC2SPacket.class, SignTextUpdateC2SPacket::write, SignTextUpdateC2SPacket::read);
        WoodworkDataHandler.obtain(modid).register(bus);
        WoodworkEventHandler.obtain(modid).register(bus);
    }

    // properties
    public final MaterialColor plankColor;
    public final WoodType type;
    public final ResourceLocation name;

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

    Woodwork(WoodworkBuilder builder,
             @Nullable
             Supplier<? extends ItemLike> log,
             DeferredRegister<Item> items,
             DeferredRegister<Block> blocks,
             DeferredRegister<BlockEntityType<?>> blockEntities) {
        this.plankColor = builder.plankColor;
        this.name = builder.name;
        this.type = WoodType.register(WoodType.create(name.toString()));

        this.planks = addBlock(items, blocks, builder, builder.planks, true);
        this.sign = addBlock(items, blocks, builder, builder.sign, true);
        this.wallSign = addBlock(items, blocks, builder, builder.wallSign, false);
        this.pressurePlate = addBlock(items, blocks, builder, builder.pressurePlate, true);
        this.trapdoor = addBlock(items, blocks, builder, builder.trapdoor, true);
        this.stairs = addBlock(items, blocks, builder, builder.stairs, true);
        this.button = addBlock(items, blocks, builder, builder.button, true);
        this.slab = addBlock(items, blocks, builder, builder.slab, true);
        this.fenceGate = addBlock(items, blocks, builder, builder.fenceGate, true);
        this.fence = addBlock(items, blocks, builder, builder.fence, true);
        this.door = addBlock(items, blocks, builder, builder.door, true);

        this.signEntity = addBlockEntity(blockEntities, builder.sign, () -> new BlockEntityType<>((p, s) ->
                new ModSignBlockEntity(signEntity(), p, s), Set.of(sign()), null));
        this.wallSignEntity = addBlockEntity(blockEntities, builder.wallSign, () -> new BlockEntityType<>((p, s) ->
                new ModSignBlockEntity(wallSignEntity(), p, s), Set.of(wallSign()), null));

        BY_ID.put(name, this);
        BY_WOOD.put(type, this);

        Lazy<WoodworkDataHandler> dataHandler = Lazy.of(() -> WoodworkDataHandler.obtain(name().getNamespace()));
        Lazy<WoodworkEventHandler> eventHandler = Lazy.of(() -> WoodworkEventHandler.obtain(name().getNamespace()));
        if (builder.enName != null) dataHandler.get().langEn.add(Pair.of(this, builder.enName));
        if (builder.zhName != null) dataHandler.get().langZh.add(Pair.of(this, builder.zhName));
        for (RegType type : builder.regTypes) {
            switch (type) {
                case BLOCK_MODELS -> dataHandler.get().mBlock.add(this);
                case ITEM_MODELS -> dataHandler.get().mItem.add(this);
                case RECIPES -> {
                    if (log != null) dataHandler.get().recipe.add(Pair.of(this, log));
                }
                case BLOCK_TAGS -> dataHandler.get().blockTags.add(this);
                case ITEM_TAGS -> dataHandler.get().itemTags.add(this);
                case LOOT_TABLES -> dataHandler.get().lootTable.add(this);
                case TAB_CONTENTS -> {
                    eventHandler.get().tabs.add(Pair.of(planks, builder.planks.tabs));
                    eventHandler.get().tabs.add(Pair.of(sign, builder.sign.tabs));
                    eventHandler.get().tabs.add(Pair.of(wallSign, builder.wallSign.tabs));
                    eventHandler.get().tabs.add(Pair.of(pressurePlate, builder.pressurePlate.tabs));
                    eventHandler.get().tabs.add(Pair.of(trapdoor, builder.trapdoor.tabs));
                    eventHandler.get().tabs.add(Pair.of(stairs, builder.stairs.tabs));
                    eventHandler.get().tabs.add(Pair.of(button, builder.button.tabs));
                    eventHandler.get().tabs.add(Pair.of(slab, builder.slab.tabs));
                    eventHandler.get().tabs.add(Pair.of(fenceGate, builder.fenceGate.tabs));
                    eventHandler.get().tabs.add(Pair.of(fence, builder.fence.tabs));
                    eventHandler.get().tabs.add(Pair.of(door, builder.door.tabs));
                }
                case RENDER_TYPE -> eventHandler.get().render.add(this);
                case LAYER -> eventHandler.get().definitions.add(this);
                case RENDERER -> {
                    if (useDefaultSignEntity() || useDefaultWallSignEntity()) eventHandler.get().renderer.add(this);
                }
            }
        }
    }

    private <B extends Block> RegistryObject<B> addBlock(DeferredRegister<Item> items,
                                                         DeferredRegister<Block> blocks,
                                                         WoodworkBuilder builder,
                                                         BlockFactory<B, ? extends BlockItem> factory,
                                                         boolean hasItem) {
        RegistryObject<B> block = register(blocks, factory.name, () -> {
            B b = factory.newBlock(this, builder.strengthModifier);
            allBlocks.add(b);
            return b;
        });
        if (hasItem) {
            register(items, block, () -> {
                BlockItem i = factory.newItem(this);
                allItems.add(i);
                return i;
            });
        }
        return block;
    }

    @Nullable
    private <T extends BlockEntity> RegistryObject<BlockEntityType<T>> addBlockEntity(DeferredRegister<BlockEntityType<?>> entities,
                                                                                      BlockFactory<?, ?> factory,
                                                                                      Supplier<BlockEntityType<T>> sup) {
        return (factory.noBlock || factory.customEntity) ? null : register(entities, factory.name, sup);
    }

    private <T> void register(DeferredRegister<? super T> register, RegistryObject<?> name, Supplier<T> supplier) {
        register.register(name.getId().getPath(), supplier);
    }

    private <T> RegistryObject<T> register(DeferredRegister<? super T> register, String postfix, Supplier<T> supplier) {
        return register.register(name.getPath() + "_" + postfix, supplier);
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
}
