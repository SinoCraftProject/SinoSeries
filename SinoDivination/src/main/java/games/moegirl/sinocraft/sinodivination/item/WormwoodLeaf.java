package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.data.SDTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WormwoodLeaf extends Item {

    public WormwoodLeaf() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        // 放在火源上
        if (state.is(SDTags.FIRE_SOURCE)) {
            context.getItemInHand().shrink(1);
            ItemStack result = new ItemStack(SDItems.MOXIBUSTION.get());
            Player player = context.getPlayer();
            if (player == null || !player.addItem(result)) {
                Block.popResource(context.getLevel(), context.getClickedPos(), result);
            }
        }
        return super.useOn(context);
    }
}
