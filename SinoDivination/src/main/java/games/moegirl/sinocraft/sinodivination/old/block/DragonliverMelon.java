package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinodivination.old.block.base.SimpleCropBlock;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class DragonliverMelon extends SimpleCropBlock<Item> {

    public DragonliverMelon() {
        super(() -> SDItems.DRAGONLIVER_MELON, 0, 1, 1, 1);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        if (level instanceof LevelReader levelReader
                && levelReader.getBiome(pos).value().getPrecipitationAt(pos) == Biome.Precipitation.SNOW) {
            return false;
        }
        return state.is(BlockTags.SAND) || state.is(BlockTags.DIRT);
    }
}
