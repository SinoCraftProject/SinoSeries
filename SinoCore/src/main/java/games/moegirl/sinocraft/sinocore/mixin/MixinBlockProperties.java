package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.Unrealized;
import games.moegirl.sinocraft.sinocore.mixin_inter.IBlockProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author luqin2007
 */
@Mixin(BlockBehaviour.Properties.class)
@Unrealized
public abstract class MixinBlockProperties implements IBlockProperties {

    /**
     * 获取硬度，用于作为后期修改的基准数值
     */
    @Override
    @Accessor
    public abstract float getDestroyTime();

    /**
     * 读取防爆，用于作为后期修改的基准数值
     */
    @Override
    @Accessor
    public abstract float getExplosionResistance();
}
