package games.moegirl.sinocraft.sinobrush.item;

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
        // Todo: waiting for dev/register
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        // Todo: waiting for dev/register
        return super.use(level, player, usedHand);
    }
}
