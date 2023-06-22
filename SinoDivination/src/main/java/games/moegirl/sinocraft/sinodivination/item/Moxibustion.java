package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinocore.utility.StreamUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 艾灸
 */
public class Moxibustion extends Item {

    public Moxibustion(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 60; // 3s
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity placer) {
        if (!level.isClientSide) {
            placer.curePotionEffects(stack);
            // 清除负面效果
            placer.getActiveEffects().stream()
                    // 负面效果
                    .filter(e -> !e.getEffect().isBeneficial())
                    // 原版效果
                    .filter(e -> "minecraft".equals(ForgeRegistries.MOB_EFFECTS.getKey(e.getEffect()).getNamespace()))
                    // 随机选取
                    .collect(StreamUtils.randomStream(level.random))
                    // 清除
                    .forEach(instance -> {
                        placer.removeEffect(instance.getEffect());
                        if (placer instanceof Player player && !player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    });
        }

        return stack;
    }
}
