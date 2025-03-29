package games.moegirl.sinocraft.sinobrush.network;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.client.ClientHelper;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.gui.screen.FanScreen;
import games.moegirl.sinocraft.sinobrush.item.BrushItem;
import games.moegirl.sinocraft.sinobrush.item.FanItem;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.network.context.ClientPlayNetworkContext;
import games.moegirl.sinocraft.sinocore.network.context.ServerPlayNetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record Common2FanLines(List<Component> lines) implements CustomPacketPayload {

    public static final Type<Common2FanLines> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SinoBrush.MODID, "fan_lines"));
    public static final StreamCodec<RegistryFriendlyByteBuf, Common2FanLines> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull Common2FanLines decode(RegistryFriendlyByteBuf buffer) {
            int count = buffer.readVarInt();
            List<Component> text = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                Component component = Component.Serializer.fromJson(buffer.readUtf(), buffer.registryAccess());
                text.add(component);
            }
            return new Common2FanLines(text);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, Common2FanLines payload) {
            buffer.writeVarInt(payload.lines.size());
            for (Component component : payload.lines) {
                buffer.writeUtf(Component.Serializer.toJson(component, buffer.registryAccess()));
            }
        }
    };

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Save.
     * @param context Server play context
     */
    public void serverHandle(ServerPlayNetworkContext context) {
        ServerPlayer player = context.getPlayer();
        // Look, no hand!
        ItemStack brush = player.getMainHandItem();
        ItemStack fan = player.getOffhandItem();
        if (brush.is(SBRItems.BRUSH.get()) && fan.is(SBRItems.FAN.get())) {
            FanItem.setLines(fan, lines);
            player.setItemInHand(InteractionHand.OFF_HAND, fan);
            BrushItem.damage(brush, player, EquipmentSlot.MAINHAND);
        }
    }

    /**
     * Open client screen.
     * @param context Client play context
     */
    public void clientHandle(ClientPlayNetworkContext context) {
        ClientHelper.showEditingFanScreen(lines);
    }
}
