package games.moegirl.sinocraft.sinofoundation.crafting.knife;

import games.moegirl.sinocraft.sinocore.crafting.container.ReadonlyItemContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class KnifeRecipeContainer implements ReadonlyItemContainer.EmptyContainer {

    Level level;
    BlockPos pos;
    Player player;
    InteractionHand hand;

    public KnifeRecipeContainer(Level level, BlockPos pos, Player player, InteractionHand hand) {
        this.level = level;
        this.pos = pos;
        this.player = player;
        this.hand = hand;
    }
}
