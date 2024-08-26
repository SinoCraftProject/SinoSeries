package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IMenuRegistry;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class FabricMenuRegistry extends FabricRegistryImpl<MenuType<?>> implements IMenuRegistry {
    public static final StreamCodec<RegistryFriendlyByteBuf, RegistryFriendlyByteBuf> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull RegistryFriendlyByteBuf decode(RegistryFriendlyByteBuf stream) {
            var buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), stream.registryAccess());
            buf.writeBytes(stream.array(), stream.readerIndex(), stream.readableBytes());
            return buf;
        }

        @Override
        public void encode(RegistryFriendlyByteBuf stream, RegistryFriendlyByteBuf data) {
            stream.writeBytes(data.array(), data.readerIndex(), data.readableBytes());
        }
    };

    FabricMenuRegistry(String modId, ResourceKey<Registry<MenuType<?>>> key) {
        super(modId, (ResourceKey<Registry<MenuType<?>>>) BuiltInRegistries.MENU.key());
    }

    @Override
    public <T extends AbstractContainerMenu> IRegRef<MenuType<?>, ?> register(String name, MenuFactory<T> factory) {
        return register(name, () -> new ExtendedScreenHandlerType<>(factory::create, STREAM_CODEC));
    }
}
