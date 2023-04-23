package games.moegirl.sinocraft.sinocalligraphy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FanItem extends Item implements Vanishable, ITabItem {

    protected boolean folded;
    protected final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public FanItem(boolean folded) {
        super(new Properties()
                .setNoRepair()
                .rarity(Rarity.RARE)
                .durability(255));

        this.folded = folded;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getSpeed(), AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
    }

    public double getDamage() {
        return !folded ? 2.5 : 0.5;
    }

    public double getSpeed() {
        return !folded ? -2 : -1.6;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, arg -> arg.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        if (!player.getCooldowns().isOnCooldown(this)) {
            ItemStack newStack;
            if (folded) {
                newStack = new ItemStack(SCAItems.FAN.get());
                newStack.setTag(stack.getTag());
                player.getCooldowns().addCooldown(SCAItems.FAN.get(), 50);
            } else {
                newStack = new ItemStack(SCAItems.FAN_FOLDED.get());
                newStack.setTag(stack.getTag());
                player.getCooldowns().addCooldown(SCAItems.FAN_FOLDED.get(), 100);
            }
            return InteractionResultHolder.success(newStack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);

        if (folded) {
            tooltip.add(Component.translatable(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_1).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_2).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_1).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_2).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public List<ResourceLocation> getTabs() {
        return List.of(SinoSeriesTabs.WEAPONS, SinoSeriesTabs.MISC);
    }
}
