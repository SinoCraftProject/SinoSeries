package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SCRecipes;
import games.moegirl.sinocraft.sinocore.crafting.container.ReadonlyItemFluidContainer;
import games.moegirl.sinocraft.sinocore.crafting.recipe.RecipeHolder;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

public class SDRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<RecipeType<?>> REGISTRY_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);

    public static final RecipeHolder<ReadonlyItemFluidContainer, KettlePotRecipe, KettlePotRecipeSerializer> KETTLE_POT = SCRecipes.register(SDItems.KETTLE_POT, KettlePotRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);
    public static final RecipeHolder<Container, ChangeSoupRecipe, ChangeSoupRecipeSerializer> CHANGE_SOUP = SCRecipes.register(SDItems.CHANGE_SOUP, ChangeSoupRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);
    public static final RecipeHolder<TripodRecipeContainer, TripodRecipe, TripodRecipeSerializer> TRIPOD = SCRecipes.register(SDBlocks.TRIPOD, TripodRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);
    public static final RecipeHolder<CarvingTableRecipeContainer, CarvingTableRecipe, CarvingTableRecipeSerializer> CARVING_TABLE = SCRecipes.register(SDBlocks.CARVING_TABLE, CarvingTableRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);

    public static void register(IEventBus bus) {
        REGISTRY_SERIALIZER.register(bus);
        REGISTRY_TYPE.register(bus);
    }
}
