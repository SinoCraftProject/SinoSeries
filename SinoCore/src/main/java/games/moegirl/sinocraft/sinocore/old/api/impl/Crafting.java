package games.moegirl.sinocraft.sinocore.old.api.impl;

import games.moegirl.sinocraft.sinocore.api.crafting.CraftingApi;
import games.moegirl.sinocraft.sinocore.api.crafting.RecipeContainer;
import games.moegirl.sinocraft.sinocore.crafting.FluidRecipeContainer;
import games.moegirl.sinocraft.sinocore.crafting.FluidsRecipeContainer;
import net.minecraft.world.Container;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public enum Crafting implements CraftingApi {

    INSTANCE;

    @Override
    public RecipeContainer createContainer(Container items, IFluidHandler fluids) {
        return new FluidsRecipeContainer(items, fluids);
    }

    @Override
    public RecipeContainer createTankContainer(Container items, IFluidTank fluid) {
        return new FluidRecipeContainer(items, fluid);
    }
}
