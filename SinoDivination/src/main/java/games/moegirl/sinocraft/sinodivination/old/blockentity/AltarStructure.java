package games.moegirl.sinocraft.sinodivination.old.blockentity;

import games.moegirl.sinocraft.sinodivination.old.recipe.AltarRecipeContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;

import javax.annotation.Nullable;

public class AltarStructure {
    public final BlockPos center;
    public final TripodEntity tripod;
    public final BlockPos[] altarPos = new BlockPos[4];
    public final AltarEntity[] altarEntities = new AltarEntity[]{null, null, null, null};
    private boolean loaded = false;

    public AltarStructure(BlockPos pos, TripodEntity tripod) {
        this.center = pos;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            altarPos[direction.get2DDataValue()] = pos.relative(direction, 4);
        }
        this.tripod = tripod;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isBuilt(@Nullable Level level) {
        load(level);
        if (level == null) return false;
        if (tripod.isRemoved() || level.getHeight(Heightmap.Types.WORLD_SURFACE, center.getX(), center.getZ()) > center.getY()) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            BlockPos pos = altarPos[i];
            if (!level.isLoaded(pos)) {
                return false;
            }
            if (level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) > pos.getY() + 1) {
                return false;
            }
            AltarEntity be = altarEntities[i];
            if (be == null || be.isRemoved()) {
                return false;
            }
        }
        return true;
    }

    public Status partStatus(Level level, Direction direction) {
        load(level);
        BlockPos pos = pos(direction);
        BlockEntity be = entity(direction);
        if (!level.isLoaded(pos)) {
            return Status.CHUNK_NOT_LOADED;
        }
        if (level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) > pos.getY() + 1) {
            return Status.HAS_OCCLUSION;
        }
        if (be == null || be.isRemoved()) {
            return Status.BLOCK_NOT_FOUND;
        }
        return Status.OK;
    }

    public BlockPos pos(Direction direction) {
        int i = direction.get2DDataValue();
        return i < 0 ? center : altarPos[i];
    }

    @Nullable
    public BlockEntity entity(Direction direction) {
        int i = direction.get2DDataValue();
        return i < 0 ? tripod : altarEntities[i];
    }

    public void setAltar(@Nullable AltarEntity altar, Direction direction) {
        altarEntities[direction.get2DDataValue()] = altar;
    }

    public AltarRecipeContainer recipeContainer() {
        return new AltarRecipeContainer() {
            final Item[] sacrificialVessels = new Item[4];
            final ItemStack base;

            {
                if (!isBuilt(null)) {
                    throw new RuntimeException("Altar Structure(center: " + center + ") is not exist...");
                }
                sacrificialVessels[0] = altarEntities[0].getItem();
                sacrificialVessels[1] = altarEntities[1].getItem();
                sacrificialVessels[2] = altarEntities[2].getItem();
                sacrificialVessels[3] = altarEntities[3].getItem();
                base = tripod.getItem();
            }

            @Override
            public Item sacrificialVessel(int i) {
                if (i >= 0 && i < 4) {
                    return sacrificialVessels[i];
                }
                return Items.AIR;
            }

            @Override
            public ItemStack base() {
                return base;
            }
        };
    }

    private void load(@Nullable Level level) {
        if (level != null && !loaded) {
            loaded = true;
            for (int i = 0; i < 4; i++) {
                int I = i;
                level.getBlockEntity(altarPos[I], SDBlockEntities.ALTAR.get()).ifPresent(e -> altarEntities[I] = e);
            }
        }
    }

    public enum Status {
        OK(""),
        BLOCK_NOT_FOUND("wrong"),
        HAS_OCCLUSION("up"),
        CHUNK_NOT_LOADED("rect");

        public final String texture;

        Status(String texture) {
            this.texture = texture;
        }
    }
}
