package games.moegirl.sinocraft.sinocore.woodwork;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WoodworkManager {

    private static final Map<String, WoodworkManager> MANAGERS = new HashMap<>();

    public static Optional<WoodworkManager> getManager(String modid) {
        return Optional.ofNullable(MANAGERS.get(modid));
    }

    public static Optional<WoodworkManager> getManager(ResourceLocation modid) {
        return getManager(modid.getNamespace());
    }

    public static Collection<WoodworkManager> allManager() {
        return Collections.unmodifiableCollection(MANAGERS.values());
    }

    public static WoodworkBuilder builder(String name) {
        return Woodwork.builder(name);
    }

    final HashMap<ResourceLocation, Woodwork> byId = new HashMap<>();
    final HashMap<WoodType, Woodwork> byType = new HashMap<>();
    final HashMap<String, Woodwork> byName = new HashMap<>();

    private final String modid;
    private final DeferredRegister<Item> items;
    private final DeferredRegister<Block> blocks;
    private final DeferredRegister<BlockEntityType<?>> blockEntities;
    private final NetworkHolder network;

    public WoodworkManager(String modid,
                           DeferredRegister<Item> items, DeferredRegister<Block> blocks,
                           DeferredRegister<BlockEntityType<?>> blockEntities, NetworkHolder network) {
        this.modid = modid;
        this.items = items;
        this.blocks = blocks;
        this.blockEntities = blockEntities;
        this.network = network;

        network.register(SignEditOpenS2CPacket.class, SignEditOpenS2CPacket::write, SignEditOpenS2CPacket::read, SignEditOpenS2CPacket::handleClient);
        network.register(SignTextUpdateC2SPacket.class, SignTextUpdateC2SPacket::write, SignTextUpdateC2SPacket::read, SignTextUpdateC2SPacket::handleServer);

        MANAGERS.put(modid, this);
    }

    public Optional<Woodwork> get(ResourceLocation name) {
        return Optional.ofNullable(byId.get(name));
    }

    public Woodwork getOrThrow(ResourceLocation name) {
        return get(name).orElseThrow();
    }

    public Optional<Woodwork> get(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    public Woodwork getOrThrow(String name) {
        return get(name).orElseThrow();
    }

    public Optional<Woodwork> get(WoodType type) {
        return Optional.ofNullable(byType.get(type));
    }

    public Woodwork getOrThrow(WoodType type) {
        return get(type).orElseThrow();
    }

    public Set<ResourceLocation> allNames() {
        return Set.copyOf(byId.keySet());
    }

    public boolean isEmpty() {
        return byId.isEmpty();
    }

    public void forEach(Consumer<Woodwork> consumer) {
        byId.forEach((__, woodwork) -> consumer.accept(woodwork));
    }

    public void forEach(BiConsumer<ResourceLocation, Woodwork> consumer) {
        byId.forEach(consumer);
    }

    public DeferredRegister<Item> items() {
        return items;
    }

    public DeferredRegister<Block> blocks() {
        return blocks;
    }

    public DeferredRegister<BlockEntityType<?>> blockEntities() {
        return blockEntities;
    }

    public NetworkHolder network() {
        return network;
    }

    public Woodwork register(WoodworkBuilder builder) {
        return builder.build(this);
    }

    public String modid() {
        return modid;
    }
}
