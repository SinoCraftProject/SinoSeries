package games.moegirl.sinocraft.sinocore.crafting.abstracted;

import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * 简化版 RecipeBuilder
 *
 * @author luqin2007
 */
public abstract class SimpleRecipeBuilder<R extends SimpleRecipe<?, R, ?>, SELF extends SimpleRecipeBuilder<R, SELF>> implements RecipeBuilder, Self<SELF> {

    protected final ResourceLocation id;
    protected ItemStack result = ItemStack.EMPTY;

    public SimpleRecipeBuilder(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public SELF unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        return self();
    }

    @Override
    public SELF group(@Nullable String pGroupName) {
        return self();
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    public abstract R build();

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(build().finished());
    }
}
