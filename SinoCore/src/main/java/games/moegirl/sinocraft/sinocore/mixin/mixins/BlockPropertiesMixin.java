package games.moegirl.sinocraft.sinocore.mixin.mixins;

import games.moegirl.sinocraft.sinocore.Unrealized;
import games.moegirl.sinocraft.sinocore.mixin.interfaces.IBlockProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.Properties.class)
@Unrealized
public abstract class BlockPropertiesMixin implements IBlockProperties {

    @Override
    @Accessor
    public abstract float getDestroyTime();

    @Override
    @Accessor
    public abstract float getExplosionResistance();
}
