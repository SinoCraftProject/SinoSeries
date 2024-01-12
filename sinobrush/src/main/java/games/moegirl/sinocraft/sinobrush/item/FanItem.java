package games.moegirl.sinocraft.sinobrush.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FanItem extends Item implements Vanishable {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public FanItem() {
        super(new Properties()
                .durability(255)
                .rarity(Rarity.RARE));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
    }

    public double getDamage() {
        return 2.5;
    }

    public double getAttackSpeed() {
        return -2;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, living -> living.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        // Todo: waiting for dev/register
//        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_1).withStyle(ChatFormatting.GRAY));
//        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_2).withStyle(ChatFormatting.GRAY));
//        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_1).withStyle(ChatFormatting.GRAY));
//        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_2).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        // Todo: waiting for dev/register
//        if (!player.getCooldowns().isOnCooldown(this)) {
//            return InteractionResultHolder.success(newStack);
//        }

        return InteractionResultHolder.pass(stack);
    }

    private ItemStack changeItemStack(Player player, ItemStack prevItemStack, ItemStack newItemStack, int cooldown) {
        if (prevItemStack.hasTag()) {
            newItemStack.setTag(prevItemStack.getTag());
        }
        player.getCooldowns().addCooldown(newItemStack.getItem(), cooldown);

        return newItemStack;
    }
}
