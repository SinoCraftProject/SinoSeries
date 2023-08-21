package games.moegirl.sinocraft.sinofeast.networking.packet;

import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTasteCodec;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTastes;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
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
            tastes.add(buf.readWithCodec(NbtOps.INSTANCE, FoodTasteCodec.TASTE_CODEC));
        }
    }

    public void serialize(FriendlyByteBuf buf) {
        buf.writeVarInt(tastes.size());

        for (var taste : tastes) {
            buf.writeWithCodec(NbtOps.INSTANCE, FoodTasteCodec.TASTE_CODEC, taste);
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
