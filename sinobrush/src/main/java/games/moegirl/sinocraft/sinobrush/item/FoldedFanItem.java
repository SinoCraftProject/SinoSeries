package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoldedFanItem extends FanItem {

    @Override
    public double getDamage() {
        return 0.5;
    }

    @Override
    public double getAttackSpeed() {
        return -1.6;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);


        var lines = getLines(stack);
        if (lines.isEmpty()) {
            tooltipComponents.add(Component.translatable(SBRConstants.Translation.KEY_DESCRIPTION_FOLDED_FAN_1).withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.translatable(SBRConstants.Translation.KEY_DESCRIPTION_FOLDED_FAN_2).withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable(SBRConstants.Translation.KEY_DESCRIPTION_FOLDED_FAN_WROTE).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        if (!player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.success(changeItemStack(player, stack, SBRItems.FAN.get(), 100));
        }

        return InteractionResultHolder.pass(stack);
    }
}
