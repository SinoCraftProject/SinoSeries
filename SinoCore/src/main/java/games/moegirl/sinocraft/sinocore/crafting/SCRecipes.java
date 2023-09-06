package games.moegirl.sinocraft.sinocore.crafting;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.RecipeHolder;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipe;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipeContainer;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class SCRecipes {

    // =================================================================================================================
    // 用于注册其他子 Mod 的 Recipe
    // 各个子 mod 可能会有自己的 Recipe，有比较明确的归属因此不适合移动到 sc 或 sfd，但可以共享给其他子 mod 使用

    private static final Map<ResourceLocation, RecipeHolder<?, ?, ?>> RECIPES = new HashMap<>();

    public static void register(ResourceLocation name, RecipeHolder<?, ?, ?> recipe) {
        RECIPES.put(name, recipe);
    }

    public static void register(RecipeHolder<?, ?, ?> recipe) {
        RECIPES.put(recipe.name(), recipe);
    }

    public static <C extends Container, T extends Recipe<C>, S extends AbstractRecipeSerializer<T>> RecipeHolder<C, T, S>
    register(ResourceLocation id, Item item, S serializer,
             DeferredRegister<RecipeSerializer<?>> registry, DeferredRegister<RecipeType<?>> registerType) {
        RecipeHolder<C, T, S> recipe = RecipeHolder.register(id, item, serializer, registry, registerType);
        RECIPES.put(id, recipe);
        return recipe;
    }

    public static <C extends Container, T extends Recipe<C>, S extends AbstractRecipeSerializer<T>> RecipeHolder<C, T, S> get(ResourceLocation name) {
        return (RecipeHolder<C, T, S>) RECIPES.get(name);
    }

    public static boolean contains(ResourceLocation name) {
        return RECIPES.containsKey(name);
    }

    public static boolean contains(String mod, String name) {
        return RECIPES.containsKey(new ResourceLocation(mod, name));
    }

    // =================================================================================================================

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SinoCore.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SinoCore.MODID);

    public static final RecipeHolder<BlockInteractRecipeContainer, BlockInteractRecipe, BlockInteractRecipeSerializer> BLOCK_INTERACT_RECIPE = RecipeHolder.register(new ResourceLocation(SinoCore.MODID, "block_interacting"), Items.PLAYER_HEAD, BlockInteractRecipeSerializer.INSTANCE, SERIALIZER_REGISTRY, TYPE_REGISTRY);

    public static void register(IEventBus bus) {
        SERIALIZER_REGISTRY.register(bus);
        TYPE_REGISTRY.register(bus);
    }
}
