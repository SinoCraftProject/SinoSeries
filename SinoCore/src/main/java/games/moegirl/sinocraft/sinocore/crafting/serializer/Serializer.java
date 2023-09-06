package games.moegirl.sinocraft.sinocore.crafting.serializer;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;

/**
 * 同时对 Json 和网络数据包的序列化、反序列化工具
 *
 * @author luqin2007
 */
public interface Serializer<T> {

    void toJson(JsonObject json, T value);

    void toNetwork(FriendlyByteBuf buffer, T value);

    T fromJson(JsonObject obj);

    T fromNetwork(FriendlyByteBuf buffer);
}
