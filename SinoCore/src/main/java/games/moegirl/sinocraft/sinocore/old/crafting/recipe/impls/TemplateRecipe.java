package games.moegirl.sinocraft.sinocore.old.crafting.recipe.impls;

import com.google.gson.annotations.Expose;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.recipe.base.BaseRecipe;
import games.moegirl.sinocraft.sinocore.crafting.recipe.base.Recipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;

/**
 * @author DustW
 *
 * 配方模板类，默认只会序列化 / 反序列化 @Expose 注解标注的字段
 * 使用 DataGenerator 时请注意 type 字段需要手动填充，否则 mc 的配方系统无法找到对应的解析器
 **/
@Recipe(SinoCore.MODID + ":test")
public class TemplateRecipe extends BaseRecipe<CraftingContainer> implements CraftingRecipe {

    @Expose NonNullList<Ingredient> inputs;
    @Expose ItemStack result;

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, this.inputs) != null;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }
}
