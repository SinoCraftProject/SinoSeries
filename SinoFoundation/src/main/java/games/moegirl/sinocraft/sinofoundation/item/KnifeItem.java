package games.moegirl.sinocraft.sinofoundation.item;

import games.moegirl.sinocraft.sinofoundation.crafting.SFDRecipes;
import games.moegirl.sinocraft.sinofoundation.crafting.knife.KnifeRecipe;
import games.moegirl.sinocraft.sinofoundation.crafting.knife.KnifeRecipeContainer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Optional;

public class KnifeItem extends SwordItem {

    public KnifeItem(Tier tier) {
        super(tier, 1, -1.6f, new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = context.getClickedPos();
            Player player = context.getPlayer();
            BlockState blockState = level.getBlockState(pos);

            // 削皮
            if (player != null) {
                KnifeRecipeContainer container = new KnifeRecipeContainer(level, pos, player, context.getHand());
                Optional<KnifeRecipe> recipeOptional = SFDRecipes.KNIFE_RECIPE.match(level, container);
                if (recipeOptional.isPresent()) {
                    recipeOptional.get().assemble(container, level.registryAccess());
                    return InteractionResult.sidedSuccess(false);
                }
            }

            // 斧
            BlockState modifiedState = blockState.getToolModifiedState(context, ToolActions.AXE_STRIP, false);
            if (modifiedState != null) {
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (player instanceof ServerPlayer sp) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, pos, context.getItemInHand());
                }

                for (Property property : blockState.getProperties()) {
                    if (modifiedState.hasProperty(property)) {
                        modifiedState = modifiedState.setValue(property, blockState.getValue(property));
                    }
                }
                level.setBlock(pos, modifiedState, 11);

                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, modifiedState));
                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(context.getHand()));
                }
                return InteractionResult.sidedSuccess(false);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction);
    }
}
