package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoldedFanItem extends FanItem {

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    @Override
    protected void appendTooltips(ItemStack stack, List<Component> tooltip) {
        var lines = getLines(stack);
        if (lines.isEmpty()) {
            tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_2).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FAN_WROTE).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        if (!player.getCooldowns().isOnCooldown(this)) {
            player.awardStat(SBRStats.UNFOLD_FAN);
            return InteractionResultHolder.success(FanItem.transmute(player, stack, SBRItems.FAN.get(), 100));
        }

        return super.use(level, player, usedHand);
    }
}
