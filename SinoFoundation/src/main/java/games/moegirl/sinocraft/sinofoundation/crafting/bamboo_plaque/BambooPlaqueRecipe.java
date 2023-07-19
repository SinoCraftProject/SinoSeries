package games.moegirl.sinocraft.sinofoundation.crafting.bamboo_plaque;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import games.moegirl.sinocraft.sinofoundation.crafting.SFDRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BambooPlaqueRecipe extends SimpleRecipe<BambooPlaqueRecipeContainer, BambooPlaqueRecipe, BambooPlaqueRecipeSerializer> {
    public BambooPlaqueRecipe(ResourceLocation id, ItemStack output) {
        super(SFDRecipes.BAMBOO_PLAQUE_RECIPE, id, 0, output);
    }

    @Override
    public boolean matches(BambooPlaqueRecipeContainer container, Level level) {
        return false;
    }
}
