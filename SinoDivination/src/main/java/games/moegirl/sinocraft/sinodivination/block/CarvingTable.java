package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinodivination.data.gen.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.menu.CarvingTableMenu;
import games.moegirl.sinocraft.sinodivination.menu.SDMenus;
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

/**
 * 雕刻台
 */
@SuppressWarnings("deprecation")
public class CarvingTable extends Block {

    public static final Component TITLE = Component.translatable(SDLangKeys.CARVING_TABLE_TITLE);

    public CarvingTable() {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            player.openMenu(getMenuProvider(blockState, level, pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((i, inventory, player) -> {
            CarvingTableMenu menu = SDMenus.CARVING_TABLE.get().create(i, inventory);
            menu.pos = pos;
            menu.owner = player;
            return menu;
        }, TITLE);
    }
}
