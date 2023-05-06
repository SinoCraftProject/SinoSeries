package games.moegirl.sinocraft.sinodivination.old.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class AltarRecipe extends SimpleRecipe<AltarRecipeContainer, AltarRecipe, AltarRecipeSerializer> {

    public static AltarRecipeBuilder builder(ResourceLocation id) {
        return new AltarRecipeBuilder(id);
    }

    final Item[] sacrificialVessels;
    final Ingredient base;
    final int tick;

    public AltarRecipe(ResourceLocation id, Item[] sacrificialVessels, Ingredient base, int tick, ItemStack result) {
        super(SDRecipes.ALTAR, id, 5, result);
        this.sacrificialVessels = sacrificialVessels;
        this.base = base;
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }

    @Override
    public boolean matches(AltarRecipeContainer pContainer, Level pLevel) {
        if (!base.test(pContainer.base())) {
            return false;
        }
        boolean[] checked = new boolean[] {false, false, false, false};
        for (int i = 0; i < 4; i++) {
            Item vessel = sacrificialVessels[i];
            if (vessel == Items.AIR) {
                continue;
            }
            boolean match = false;
            for (int j = 0; j < checked.length && !checked[j]; j++) {
                if (vessel == pContainer.sacrificialVessel(j)) {
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
