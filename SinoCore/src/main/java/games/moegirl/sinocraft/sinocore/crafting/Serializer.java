package games.moegirl.sinocraft.sinocore.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;

/**
 * @author luqin2007
 */
public interface Serializer<T> {

    void toJson(JsonObject json, T value);

    void toNetwork(FriendlyByteBuf buffer, T value);

    T fromJson(JsonObject obj);

    T fromNetwork(FriendlyByteBuf buffer);
}
