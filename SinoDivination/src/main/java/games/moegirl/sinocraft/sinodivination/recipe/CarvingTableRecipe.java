package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class CarvingTableRecipe extends SimpleRecipe<CarvingTableRecipeContainer, CarvingTableRecipe, CarvingTableRecipeSerializer> {

    public static CarvingTableRecipeBuilder builder(ResourceLocation id, ItemStack output) {
        return new CarvingTableRecipeBuilder(id, output);
    }

    public static CarvingTableRecipeBuilder builder(RegistryObject<Item> output) {
        return new CarvingTableRecipeBuilder(new ItemStack(output.get()));
    }

    final Ingredient[] ingredients;
    final int[][] inputs;
    final Ingredient dye;
    @Nullable
    final OriginData originData;

    public CarvingTableRecipe(ResourceLocation id, Ingredient[] ingredients, int[][] inputs, Ingredient dye, ItemStack output, @Nullable OriginData originData) {
        super(SDRecipes.CARVING_TABLE, id, 17, output);
        this.ingredients = ingredients;
        this.inputs = inputs;
        this.dye = dye;
        this.originData = originData;
    }

    @Override
    public boolean matches(CarvingTableRecipeContainer container, Level level) {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                int i = inputs[row][column];
                if (i >= 0 && i < 16) {
                    if (!ingredients[i].test(container.getInput(row, column))) {
                        return false;
                    }
                }
            }
        }
        return dye.isEmpty() || dye.test(container.getDye());
    }

    public Ingredient getInput(int row, int column) {
        int i = inputs[row][column];
        if (i >= 0 && i < 16) {
            return ingredients[i];
        } else {
            return Ingredient.EMPTY;
        }
    }

    public Ingredient getDye() {
        return dye;
    }

    record OriginData(String[] patterns, char[] keys, Ingredient[] ingredients) {}
}
