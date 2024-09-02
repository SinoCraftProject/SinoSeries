package games.moegirl.sinocraft.sinotest.network;

import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import games.moegirl.sinocraft.sinocore.network.PacketDistributor;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TestNetworkItem extends Item {

    public TestNetworkItem() {
        super(new Properties().sino$tab(TestRegistry.TEST_TAB));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            StringBuilder randomStr = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                randomStr.append((char) (level.random.nextInt(26) + 'a'));
            }
            S2CHelloPacket packet = new S2CHelloPacket(randomStr.toString());
            if (player.isShiftKeyDown()) {
                NetworkManager.send(packet, PacketDistributor.PLAYER.with((ServerPlayer) player));
            } else {
                NetworkManager.send(packet, (ServerPlayer) player);
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide());
    }
}
