package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.recipe.TripodRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.TripodRecipeContainer;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.util.RecipeProcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * @author luqin2007
 */
public class TripodProcessor extends RecipeProcessor<TripodEntity, TripodRecipeContainer, TripodRecipe> {

    private ItemStack result = ItemStack.EMPTY;

    public TripodProcessor() {
        super(SDRecipes.TRIPOD, false);
    }

    @Override
    protected void loadProcessor(CompoundTag nbt) {
        if (nbt.contains("Tripod.Result", Tag.TAG_COMPOUND)) {
            result = ItemStack.of(nbt.getCompound("Tripod.Result"));
        }
    }

    @Override
    protected void saveProcessor(CompoundTag nbt) {
        if (!result.isEmpty()) {
            nbt.put("Tripod.Result", result.serializeNBT());
        }
    }

    @Override
    protected Optional<TripodRecipe> onFindRecipe(TripodEntity entity) {
        Level level = entity.getLevel();
        if (level == null) return Optional.empty();
        return SDRecipes.TRIPOD.match(level, new TripodRecipeContainer(new Item[] {
                level.getBlockEntity(entity.getBlockPos().offset(0, 0, 4), SDBlockEntities.ALTAR.get()).orElseThrow().getItem(),
                level.getBlockEntity(entity.getBlockPos().offset(0, 0, -4), SDBlockEntities.ALTAR.get()).orElseThrow().getItem(),
                level.getBlockEntity(entity.getBlockPos().offset(4, 0, 0), SDBlockEntities.ALTAR.get()).orElseThrow().getItem(),
                level.getBlockEntity(entity.getBlockPos().offset(-4, 0, 0), SDBlockEntities.ALTAR.get()).orElseThrow().getItem()
        }, entity.getItem().copy()));
    }

    @Override
    protected boolean isRecipeEffective(TripodEntity entity, TripodRecipe recipe) {
        Level level = entity.getLevel();
        if (level == null) return false;
        return recipe.matches(new TripodRecipeContainer(new Item[] {
                level.getBlockEntity(entity.getBlockPos().offset(0, 0, 4), SDBlockEntities.ALTAR.get()).orElseThrow().getItem(),
                level.getBlockEntity(entity.getBlockPos().offset(4, 0, 0), SDBlockEntities.ALTAR.get()).orElseThrow().getItem(),
                level.getBlockEntity(entity.getBlockPos().offset(0, 0, -4), SDBlockEntities.ALTAR.get()).orElseThrow().getItem(),
                level.getBlockEntity(entity.getBlockPos().offset(-4, 0, 0), SDBlockEntities.ALTAR.get()).orElseThrow().getItem()
        }, entity.getItem().copy()), level);
    }

    @Override
    protected void onFinished(TripodEntity entity, TripodRecipe recipe) {
        Level level = entity.getLevel();
        assert level != null;
        entity.in.extractItem2(0, 1, false);
        level.getBlockEntity(entity.getBlockPos().offset(0, 0, 4), SDBlockEntities.ALTAR.get()).orElseThrow().in.extractItem2(0, 1, false);
        level.getBlockEntity(entity.getBlockPos().offset(4, 0, 0), SDBlockEntities.ALTAR.get()).orElseThrow().in.extractItem2(0, 1, false);
        level.getBlockEntity(entity.getBlockPos().offset(0, 0, -4), SDBlockEntities.ALTAR.get()).orElseThrow().in.extractItem2(0, 1, false);
        level.getBlockEntity(entity.getBlockPos().offset(-4, 0, 0), SDBlockEntities.ALTAR.get()).orElseThrow().in.extractItem2(0, 1, false);
        result = entity.out.insertItem2(0, recipe.getResultItem(), false);
        isDataChanged = true;
    }

    @Override
    protected boolean onBlocking(TripodEntity entity) {
        if (!result.isEmpty()) {
            result = entity.out.insertItem2(0, result, false);
            isDataChanged = true;
        }
        return !result.isEmpty();
    }
}
