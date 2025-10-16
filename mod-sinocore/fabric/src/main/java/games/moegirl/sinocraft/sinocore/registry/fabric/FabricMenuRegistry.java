package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IMenuRegistry;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class FabricMenuRegistry extends FabricRegistry<MenuType<?>> implements IMenuRegistry {
    public static final StreamCodec<RegistryFriendlyByteBuf, byte[]> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public byte @NotNull [] decode(RegistryFriendlyByteBuf buf) {
            var length = buf.readVarInt();
            return ByteBufUtil.getBytes(buf.readBytes(length));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, byte[] data) {
            buf.writeVarInt(data.length);
            buf.writeBytes(data);
        }
    };

    FabricMenuRegistry(String modId) {
        super(modId, Registries.MENU);
    }

    @Override
    public <T extends AbstractContainerMenu> IRegRef<MenuType<?>> register(String name, MenuFactory<T> factory) {
        return register(name, () -> new ExtendedScreenHandlerType<>((id, inventory, data) ->
                factory.create(id, inventory, new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(data), inventory.player.registryAccess())), STREAM_CODEC));
    }
}
