package games.moegirl.sinocraft.sinocore.mixin;

//import games.moegirl.sinocraft.sinocore.old.api.mixin.IBlockLoot;
import games.moegirl.sinocraft.sinocore.Unrealized;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// @Mixin(BlockLoot.class)
@Mixin(Block.class)
@Unrealized
public abstract class MixinBlockLoot /*implements IBlockLoot*/ {

//    @Shadow(remap = false) protected abstract Iterable<Block> getKnownBlocks();
//
//    @Override
//    public Iterable<Block> scGetKnownBlocks() {
//        return getKnownBlocks();
//    }
}
