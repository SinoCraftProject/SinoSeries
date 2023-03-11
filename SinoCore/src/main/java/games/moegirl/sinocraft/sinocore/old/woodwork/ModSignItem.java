package games.moegirl.sinocraft.sinocore.old.woodwork;

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
import net.minecraftforge.registries.ForgeRegistries;

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
                WoodworkManager.getManager(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(sign.getType()))
                        .map(WoodworkManager::network)
                        .ifPresent(wn -> wn.sendToClient(new SignEditOpenS2CPacket(pPos), sp));
            }
        }

        return flag;
    }
}
