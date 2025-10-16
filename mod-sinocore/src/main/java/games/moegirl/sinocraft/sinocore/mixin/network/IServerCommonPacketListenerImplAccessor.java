package games.moegirl.sinocraft.sinocore.mixin.network;

import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerCommonPacketListenerImpl.class)
public interface IServerCommonPacketListenerImplAccessor {

    /**
     * 针对 fabric，获取 {@link Connection} 用于创建网络通信上下文
     */
    @Accessor
    Connection getConnection();
}
