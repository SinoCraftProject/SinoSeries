package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.item.component.FanData;
import games.moegirl.sinocraft.sinobrush.item.component.SBRDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FanItem extends Item {
    public FanItem() {
        super(new Properties()
                .stacksTo(1)
                .component(SBRDataComponents.FAN.get(), new FanData())
//                .sino$tab(SBRItems.SINO_BRUSH_TAB)
                .attributes(FanItem.createAttributes(0, -0.8F)));
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    protected void appendTooltips(ItemStack stack, List<Component> tooltip) {
        var lines = getLines(stack);
        if (lines.isEmpty()) {
            tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_1).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FAN_EMPTY).withStyle(ChatFormatting.GRAY));
            tooltip.addAll(lines.stream().map(l -> MutableComponent.create(l.getContents()).withStyle(ChatFormatting.GRAY)).toList());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        this.appendTooltips(stack, tooltipComponents);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        if (player.isShiftKeyDown()) {
            if (level.isClientSide) {
                ((Runnable) () -> net.minecraft.client.Minecraft.getInstance().setScreen(
                        new games.moegirl.sinocraft.sinobrush.gui.screen.FanScreen(getLines(stack)))).run();
            }
            return InteractionResultHolder.success(stack);
        }

        if (!player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.success(transmute(player, stack, SBRItems.FOLDED_FAN.get(), 100));
        }

        return InteractionResultHolder.pass(stack);
    }

    public static ItemStack transmute(Player player, ItemStack stack, Item newItem, int cooldown) {
        player.getCooldowns().addCooldown(newItem, cooldown);
        return stack.transmuteCopy(newItem);
    }

    public static List<Component> getLines(ItemStack stack) {
        var data = FanData.get(stack);
        return data.lines();
    }

    public static void setLines(ItemStack stack, List<Component> lines) {
        FanData.set(stack, new FanData(lines));
    }

    public static boolean hasLines(ItemStack stack) {
        return !getLines(stack).isEmpty();
    }
}
