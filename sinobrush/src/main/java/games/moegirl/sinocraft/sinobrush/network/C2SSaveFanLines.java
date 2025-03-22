package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.item.FanItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.network.context.ServerPlayNetworkContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record C2SSaveFanLines(InteractionHand hand, List<Component> lines) implements CustomPacketPayload {

    public static final Type<C2SSaveFanLines> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SinoBrush.MODID, "save_fan_lines"));
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SSaveFanLines> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public C2SSaveFanLines decode(RegistryFriendlyByteBuf buffer) {
            InteractionHand hand = buffer.readEnum(InteractionHand.class);
            int count = buffer.readVarInt();
            List<Component> text = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                Component component = Component.Serializer.fromJson(buffer.readUtf(), buffer.registryAccess());
                text.add(component);
            }
            return new C2SSaveFanLines(hand, text);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, C2SSaveFanLines payload) {
            buffer.writeEnum(payload.hand);
            buffer.writeVarInt(payload.lines.size());
            for (Component component : payload.lines) {
                buffer.writeUtf(Component.Serializer.toJson(component, buffer.registryAccess()));
            }
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(ServerPlayNetworkContext context) {
        ServerPlayer player = context.getPlayer();
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(SBRItems.FAN.get()) || stack.is(SBRItems.FOLDED_FAN.get())) {
            FanItem.setLines(stack, lines);
            player.setItemInHand(hand, stack);
        }
    }
}
