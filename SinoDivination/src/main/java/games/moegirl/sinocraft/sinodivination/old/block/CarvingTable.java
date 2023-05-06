package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.old.menu.CarvingTableMenu;
import games.moegirl.sinocraft.sinodivination.old.menu.SDMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CarvingTable extends Block {

    public static final Component TITLE = Component.translatable(SDLangKeys.CARVING_TABLE_TITLE);

    public CarvingTable() {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(getMenuProvider(pState, pLevel, pPos));
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((i, arg, arg2) -> {
            CarvingTableMenu menu = SDMenus.CARVING_TABLE.get().create(i, arg);
            menu.pos = pos;
            menu.owner = arg2;
            return menu;
        }, TITLE);
    }
}
