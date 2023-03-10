package games.moegirl.sinocraft.sinocore.old.api.woodwork;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;

public abstract class ModSignBlock extends SignBlock implements IWoodwork {

    private final Woodwork woodwork;

    protected ModSignBlock(Woodwork woodwork, Properties properties) {
        super(properties, woodwork.type);
        this.woodwork = woodwork;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ModSignBlockEntity(woodwork.signEntity(), pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level world, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = player.getItemInHand(pHand);
        if (!world.isClientSide && itemstack.isEmpty() && player instanceof ServerPlayer sp) {
            if (world.getBlockEntity(pPos) instanceof ModSignBlockEntity sign && sign.canPlayerEdit(player)) {
                woodwork.manager().network().sendToClient(new SignEditOpenS2CPacket(pPos), sp);
                return InteractionResult.SUCCESS;
            }
        }
        Item item = itemstack.getItem();
        boolean isDye = item instanceof DyeItem;
        boolean isGrowInkSac = itemstack.is(Items.GLOW_INK_SAC);
        boolean isInkSac = itemstack.is(Items.INK_SAC);
        boolean setColor = (isGrowInkSac || isDye || isInkSac) && player.getAbilities().mayBuild;
        if (world.isClientSide) {
            return setColor ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        } else {
            BlockEntity be = world.getBlockEntity(pPos);
            if (be instanceof ModSignBlockEntity sign) {
                boolean hasText = sign.hasGlowingText();
                if ((!isGrowInkSac || !hasText) && (!isInkSac || hasText)) {
                    if (setColor) {
                        boolean hasChangedColor;
                        if (isGrowInkSac) {
                            world.playSound(null, pPos, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            hasChangedColor = sign.setHasGlowingText(true);
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pPos, itemstack);
                        } else if (isInkSac) {
                            world.playSound(null, pPos, SoundEvents.INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            hasChangedColor = sign.setHasGlowingText(false);
                        } else {
                            world.playSound(null, pPos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            hasChangedColor = sign.setColor(((DyeItem)item).getDyeColor());
                        }

                        if (hasChangedColor) {
                            if (!player.isCreative()) {
                                itemstack.shrink(1);
                            }
                            player.awardStat(Stats.ITEM_USED.get(item));
                        }
                    }
                    return sign.executeClickCommands((ServerPlayer)player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                }
            }
            return InteractionResult.PASS;
        }
    }

    @Override
    public Woodwork getWoodwork() {
        return woodwork;
    }

    public static Woodwork getWoodwork(BlockState state) {
        return ((ModSignBlock) state.getBlock()).woodwork;
    }

    public static WoodType getWoodType(BlockState state) {
        return getWoodwork(state).type;
    }
}