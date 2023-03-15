package games.moegirl.sinocraft.sinocore.item;

import games.moegirl.sinocraft.sinocore.blockentity.ModSignBlockEntity;
import games.moegirl.sinocraft.sinocore.packet.SignEditOpenS2CPacket;
import games.moegirl.sinocraft.sinocore.woodwork.Woodwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ModSignItem extends StandingAndWallBlockItem {

    public ModSignItem(Properties pProperties, Block pStandingBlock, Block pWallBlock) {
        super(pStandingBlock, pWallBlock, pProperties, Direction.DOWN);
    }

    public ModSignItem(Properties properties, Woodwork woodwork) {
        this(properties, woodwork.sign(), woodwork.wallSign());
    }

    protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState) {
        boolean flag = super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);
        if (pPlayer != null) {
            if (!pLevel.isClientSide && !flag && pPlayer instanceof ServerPlayer sp && pLevel.getBlockEntity(pPos) instanceof ModSignBlockEntity sign) {
                sign.setPlayerWhoMayEdit(pPlayer.getUUID());
                sp.connection.send(new ClientboundBlockUpdatePacket(pLevel, pPos));
                Woodwork.network(sign.getWoodwork().name.getNamespace())
                        .sendToClient(new SignEditOpenS2CPacket(pPos), sp);
            }
        }

        return flag;
    }
}
