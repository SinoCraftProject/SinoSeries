package games.moegirl.sinocraft.sinobrush.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import games.moegirl.sinocraft.sinobrush.SBRConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
                .stacksTo(1)
                .sino$tab(SBRItems.SINO_BRUSH_TAB));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
    }

    public double getDamage() {
        return 0;
    }

    public double getAttackSpeed() {
        return -4;
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

    protected void appendTooltips(ItemStack stack, List<Component> tooltip) {
        var lines = getLines(stack);
        if (lines.isEmpty()) {
            tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FAN).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_FAN_WROTE).withStyle(ChatFormatting.GRAY));
            tooltip.addAll(lines.stream().map(l -> l.withStyle(ChatFormatting.GRAY)).toList());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        this.appendTooltips(stack, tooltipComponents);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        if (!player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.success(changeItemStack(player, stack, SBRItems.FOLDED_FAN.get(), 100));
        }

        return InteractionResultHolder.pass(stack);
    }

    protected ItemStack changeItemStack(Player player, ItemStack prevItemStack, Item newItem, int cooldown) {
        var newItemStack = new ItemStack(newItem);

        if (prevItemStack.hasTag()) {
            newItemStack.setTag(prevItemStack.getTag());
        }
        player.getCooldowns().addCooldown(newItem, cooldown);

        return newItemStack;
    }

    public List<MutableComponent> getLines(ItemStack stack) {
       if (stack.hasTag()) {
           var tag = stack.getTag();
           if (tag.contains(SBRConstants.TagName.FAN)) {
               var fan = tag.getCompound(SBRConstants.TagName.FAN);
               if (fan.contains(SBRConstants.TagName.FAN_LINES)) {
                   var lines = fan.getList(SBRConstants.TagName.FAN_LINES, Tag.TAG_STRING);
                   return lines.stream().map(l -> Component.Serializer.fromJson(l.getAsString())).toList();
               }
           }
       }

       return List.of();
    }

    public void setLines(ItemStack stack, List<Component> lines) {
        var linesTag = new ListTag();
        linesTag.addAll(lines.stream().map(Component.Serializer::toJson).map(StringTag::valueOf).toList());

        var fan = new CompoundTag();
        fan.put(SBRConstants.TagName.FAN_LINES, linesTag);

        var tag = stack.getOrCreateTag();
        tag.put(SBRConstants.TagName.FAN, fan);

        stack.setTag(tag);
    }

    public boolean hasLines(ItemStack stack) {
        return !getLines(stack).isEmpty();
    }
}
