package games.moegirl.sinocraft.sinofeast.networking.packet;

import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTastes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class S2CSyncFoodTastePacket {
    private final List<FoodTaste> tastes = new ArrayList<>();

    public S2CSyncFoodTastePacket(Collection<FoodTaste> tastes) {
        this.tastes.addAll(tastes);
    }

    public S2CSyncFoodTastePacket(FriendlyByteBuf buf) {
        var count = buf.readVarInt();

        for (var i = 0; i < count; i++) {
            tastes.add(new FoodTaste(new ResourceLocation(buf.readUtf()),
                    buf.readUtf(), buf.readBoolean(), buf.readVarInt(), buf.readVarInt(),
                    ItemTags.create(new ResourceLocation(buf.readUtf())),
                    ItemTags.create(new ResourceLocation(buf.readUtf())),
                    ItemTags.create(new ResourceLocation(buf.readUtf()))));
        }
    }

    public void serialize(FriendlyByteBuf buf) {
        buf.writeVarInt(tastes.size());

        for (var taste : tastes) {
            buf.writeUtf(taste.key().toString());
            buf.writeUtf(taste.name());
            buf.writeBoolean(taste.isAdvanced());
            buf.writeVarInt(taste.likeWeight());
            buf.writeVarInt(taste.dislikeWeight());
            buf.writeUtf(taste.tasteKey().location().toString());
            buf.writeUtf(taste.tasteKeyPrimary().location().toString());
            buf.writeUtf(taste.tasteKeySecondary().location().toString());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            FoodTastes.getInstance().initTastes();

            for (var taste : tastes) {
                FoodTastes.getInstance().addTaste(taste.key(), taste);
            }
        });
    }
}
