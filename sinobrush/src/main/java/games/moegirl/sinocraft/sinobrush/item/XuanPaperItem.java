package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.gui.menu.XuanPaperMenu;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import games.moegirl.sinocraft.sinocore.utility.MenuHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XuanPaperItem extends Item implements DyeableLeatherItem {
    public XuanPaperItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor(ItemStack stack) {
        if (hasCustomColor(stack)) {
            return DyeableLeatherItem.super.getColor(stack);
        } else {
            setColor(stack, SBRConstants.COLOR_WHITE);
            return SBRConstants.COLOR_WHITE;
        }
    }

    public int getExpend(ItemStack stack) {
        if (!stack.is(SBRItems.XUAN_PAPER.get())) {
            return -1;
        }

        if (stack.hasTag()) {
            var tag = stack.getTag();
            assert tag != null;

            var paper = tag.getCompound(SBRConstants.TagName.XUAN_PAPER);
            var expends = paper.getInt(SBRConstants.TagName.XUAN_PAPER_EXPENDS);
            if (expends > 0 && expends < SBRConstants.XUAN_PAPER_MAX_EXPEND) {
                return expends;
            }
        }

        return 0;
    }

    public boolean canExpend(ItemStack stack) {
        return stack.is(SBRItems.XUAN_PAPER.get()) && getExpend(stack) < SBRConstants.XUAN_PAPER_MAX_EXPEND;
    }

    public void setExpend(ItemStack stack, int expend) {
        if (!stack.is(SBRItems.XUAN_PAPER.get())) {
            return;
        }

        var tag = stack.getOrCreateTag();
        var paper = tag.getCompound(SBRConstants.TagName.XUAN_PAPER);
        paper.putInt(SBRConstants.TagName.XUAN_PAPER_EXPENDS, expend);
        tag.put(SBRConstants.TagName.XUAN_PAPER, paper);
        stack.setTag(tag);
    }

    public void expend(ItemStack stack) {
        if (canExpend(stack)) {
            setExpend(stack, getExpend(stack) + 1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        var color = getColor(stack);
        tooltipComponents.add(TooltipHelper.getColor(color));

        var expend = getExpend(stack);
        if (expend != 0) {
            tooltipComponents.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED,
                    expend).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            MenuHelper.openMenu(sp, (i, inventory, p) -> new XuanPaperMenu(i, inventory, null));
        }
        return super.use(level, player, usedHand);
    }
}
