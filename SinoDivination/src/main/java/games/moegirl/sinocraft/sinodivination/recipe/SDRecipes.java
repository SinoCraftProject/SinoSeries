package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.ReadonlyItemFluidContainer;
import games.moegirl.sinocraft.sinocore.crafting.RecipeHolder;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static games.moegirl.sinocraft.sinodivination.SinoDivination.MODID;

public class SDRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<RecipeType<?>> REGISTRY_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);

    public static RecipeHolder<ReadonlyItemFluidContainer, KettlePotRecipe, KettlePotRecipeSerializer> KETTLE_POT = RecipeHolder.register(SDItems.KETTLE_POT, KettlePotRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);

    public static RecipeHolder<Container, ChangeSoupRecipe, ChangeSoupRecipeSerializer> CHANGE_SOUP = RecipeHolder.register(SDItems.CHANGE_SOUP, ChangeSoupRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);

    public static RecipeHolder<TripodRecipeContainer, TripodRecipe, TripodRecipeSerializer> TRIPOD = RecipeHolder.register(SDBlocks.TRIPOD, TripodRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);

    public static RecipeHolder<CarvingTableRecipeContainer, CarvingTableRecipe, CarvingTableRecipeSerializer> CARVING_TABLE = RecipeHolder.register(SDBlocks.CARVING_TABLE, CarvingTableRecipeSerializer.INSTANCE, REGISTRY_SERIALIZER, REGISTRY_TYPE);
}
