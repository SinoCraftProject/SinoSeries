package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoldedFanItem extends FanItem {

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);


        var lines = getLines(stack);
        if (lines.isEmpty()) {
            tooltipComponents.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN).withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FAN_WROTE).withStyle(ChatFormatting.GRAY));
            tooltipComponents.addAll(lines.stream().map(l -> l.withStyle(ChatFormatting.GRAY)).toList());
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
