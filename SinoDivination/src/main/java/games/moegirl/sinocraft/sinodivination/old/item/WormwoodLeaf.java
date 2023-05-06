package games.moegirl.sinocraft.sinodivination.old.item;

import games.moegirl.sinocraft.sinodivination.old.data.SDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public class WormwoodLeaf extends Item {

    public WormwoodLeaf() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (state.is(SDTags.FIRE_SOURCE)) {
            pContext.getItemInHand().shrink(1);
            ItemStack result = new ItemStack(SDItems.MOXIBUSTION.get());
            Player player = pContext.getPlayer();
            if (player == null || !player.addItem(result)) {
                float f = EntityType.ITEM.getHeight() / 2.0F;
                BlockPos pPos = pContext.getClickedPos();
                double d0 = pPos.getX() + Mth.nextDouble(pContext.getLevel().random, -0.25D, 0.25D);
                double d1 = pPos.getY() + Mth.nextDouble(pContext.getLevel().random, -0.25D, 0.25D) - f;
                double d2 = pPos.getZ() + Mth.nextDouble(pContext.getLevel().random, -0.25D, 0.25D);
                ItemEntity itementity = new ItemEntity(pContext.getLevel(), d0, d1, d2, result);
                itementity.setDefaultPickUpDelay();
                pContext.getLevel().addFreshEntity(itementity);
            }
        }
        return super.useOn(pContext);
    }
}
