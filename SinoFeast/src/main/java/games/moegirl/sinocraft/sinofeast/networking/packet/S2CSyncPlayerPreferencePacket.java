package games.moegirl.sinocraft.sinofeast.networking.packet;

import games.moegirl.sinocraft.sinofeast.capability.SFCapabilities;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTastes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncPlayerPreferencePacket {
    private final FoodTaste prefer;
    private final FoodTaste like;
    private final FoodTaste dislike;

    public S2CSyncPlayerPreferencePacket(FoodTaste prefer, FoodTaste like, FoodTaste dislike) {
        this.prefer = prefer;
        this.like = like;
        this.dislike = dislike;
    }

    public S2CSyncPlayerPreferencePacket(FriendlyByteBuf buf) {
        prefer = FoodTastes.getInstance().getTaste(new ResourceLocation(buf.readUtf()));
        like = FoodTastes.getInstance().getTaste(new ResourceLocation(buf.readUtf()));
        dislike = FoodTastes.getInstance().getTaste(new ResourceLocation(buf.readUtf()));
    }

    public void serialize(FriendlyByteBuf buf) {
        buf.writeUtf(prefer.getKey().toString());
        buf.writeUtf(like.getKey().toString());
        buf.writeUtf(dislike.getKey().toString());
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        var ctx = context.get();
        ctx.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                var cap = player.getCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE).orElseThrow(RuntimeException::new);
                cap.setPrefer(prefer);
                cap.setPrefer(like);
                cap.setPrefer(dislike);
            }
        });
    }
}
