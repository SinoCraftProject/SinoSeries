package games.moegirl.sinocraft.sinodivination.old.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class Moxibustion extends Item {

    public Moxibustion() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 60; // 3s
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            pLivingEntity.curePotionEffects(pStack);
            pLivingEntity.getActiveEffects().stream()
                    .filter(e -> !e.getEffect().isBeneficial())
                    .findFirst()
                    .ifPresent(instance -> {
                        pLivingEntity.removeEffect(instance.getEffect());
                        if (pLivingEntity instanceof Player player && !player.getAbilities().instabuild) {
                            pStack.shrink(1);
                        }
                    });
        }

        return pStack;
    }
}
