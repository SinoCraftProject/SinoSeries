package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinofoundation.data.gen.tag.SFDBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT;

/**
 * 灯笼
 *
 * @author luqin2007
 */
public class Lantern extends Block {

    public Lantern(float strength, MapColor color) {
        super(Properties.copy(Blocks.GLOWSTONE)
                .mapColor(color)
                .forceSolidOn()
                .requiresCorrectToolForDrops()
                .strength(strength)
                .sound(SoundType.LANTERN)
                .lightLevel(bs -> isLit(bs) ? 15 : 0)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY));
        registerDefaultState(defaultBlockState().setValue(LIT, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            if (isLit(state) && player.isShiftKeyDown()) {
                setLit(level, pos, state, false);
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else if (!isLit(state) && player.getItemInHand(hand).is(Items.FLINT_AND_STEEL)) {
                setLit(level, pos, state, true);
                player.getItemInHand(hand).hurt(1, level.random, (ServerPlayer) player);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
            return super.use(state, level, pos, player, hand, hit);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    /**
     * 获取灯笼是否点亮
     * @return 是否点亮
     */
    public static boolean isLit(BlockState state) {
        return state.hasProperty(LIT) && state.getValue(LIT);
    }

    /**
     * 设置点亮或熄灭灯笼
     * @param isLit 灯笼是否被点亮
     */
    public static void setLit(Level level, BlockPos pos, BlockState state, boolean isLit) {
        if (isLit(state) != isLit) {
            level.setBlock(pos, state.setValue(LIT, isLit), Block.UPDATE_CLIENTS);
        }
    }
}
