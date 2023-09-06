package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.recipe.SimpleRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class TripodRecipe extends SimpleRecipe<TripodRecipeContainer, TripodRecipe, TripodRecipeSerializer> {

    public static TripodRecipeBuilder builder(ResourceLocation id) {
        return new TripodRecipeBuilder(id);
    }

    final Item[] sacrificialVessels;
    final Ingredient base;
    final int tick;

    public TripodRecipe(ResourceLocation id, Item[] sacrificialVessels, Ingredient base, int tick, ItemStack result) {
        super(SDRecipes.TRIPOD, id, 5, result);
        this.sacrificialVessels = sacrificialVessels;
        this.base = base;
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }

    @Override
    public boolean matches(TripodRecipeContainer container, Level level) {
        if (!base.test(container.base())) {
            return false;
        }
        boolean[] checked = new boolean[] {false, false, false, false};
        for (int i = 0; i < 4; i++) {
            Item vessel = sacrificialVessels[i];
            if (vessel == Items.AIR) {
                continue;
            }
            boolean match = false;
            for (int j = 0; j < checked.length; j++) {
                if (checked[j]) {
                    continue;
                }
                if (vessel == container.sacrificialVessel(j)) {
                    checked[j] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                return false;
            }
        }
        return true;
    }
}
