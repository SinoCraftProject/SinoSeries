package games.moegirl.sinocraft.sinodivination.old.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Stream;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {

    @Shadow
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
    @Shadow
    private Map<ResourceLocation, Recipe<?>> byName;

    @Shadow
    private boolean hasErrors;
    private boolean sinodivination$isReplaced = false;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("RETURN"))
    protected void injectApply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        sinodivination$isReplaced = false;
    }

    @Inject(method = "replaceRecipes", at = @At("RETURN"))
    protected void injectReplaceRecipes(Iterable<Recipe<?>> recipes, CallbackInfo ci) {
        sinodivination$isReplaced = false;
    }

    @Inject(method = "getRecipeFor", at = @At("HEAD"))
    protected <C extends Container, T extends Recipe<C>> void injectGetRecipeFor(RecipeType<T> recipeType, C inventory, Level level, CallbackInfoReturnable<Optional<T>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "getAllRecipesFor", at = @At("HEAD"))
    protected <C extends Container, T extends Recipe<C>> void injectGetAllRecipesFor(RecipeType<T> recipeType, CallbackInfoReturnable<List<T>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "getRecipesFor", at = @At("HEAD"))
    protected <C extends Container, T extends Recipe<C>> void injectGetRecipesFor(RecipeType<T> recipeType, C inventory, Level level, CallbackInfoReturnable<List<T>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "byType", at = @At("HEAD"))
    protected <C extends Container, T extends Recipe<C>> void injectByType(RecipeType<T> recipeType, CallbackInfoReturnable<Map<ResourceLocation, Recipe<C>>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "getRemainingItemsFor", at = @At("HEAD"))
    protected <C extends Container, T extends Recipe<C>> void injectGetRemainingItemsFor(RecipeType<T> recipeType, C inventory, Level level, CallbackInfoReturnable<NonNullList<ItemStack>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "byKey", at = @At("HEAD"))
    protected void injectByKey(ResourceLocation recipeId, CallbackInfoReturnable<Optional<? extends Recipe<?>>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "getRecipes", at = @At("HEAD"))
    protected void injectGetRecipes(CallbackInfoReturnable<Collection<Recipe<?>>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    @Inject(method = "getRecipeIds", at = @At("HEAD"))
    protected void injectGetRecipeIds(CallbackInfoReturnable<Stream<ResourceLocation>> ci) {
        if (!sinodivination$isReplaced) {
            sinodivination$replaceRecipes();
        }
    }

    private void sinodivination$replaceRecipes() {
        if (hasErrors) return;
        Map<ResourceLocation, Recipe<?>> blasting = new HashMap<>(recipes.getOrDefault(RecipeType.BLASTING, Collections.emptyMap()));
        Map<ResourceLocation, Recipe<?>> smelting = new HashMap<>(recipes.getOrDefault(RecipeType.SMELTING, Collections.emptyMap()));

        Iterator<Map.Entry<ResourceLocation, Recipe<?>>> iterator = smelting.entrySet().iterator();
        int count = 0;
        var blastingRecipePairs = blasting.values().stream()
                .map(r -> Pair.of(r, r.getIngredients().get(0).getItems())).toList();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, Recipe<?>> next = iterator.next();
            ResourceLocation id = next.getKey();
            Recipe<?> recipe = next.getValue();
            Ingredient ingredient = recipe.getIngredients().get(0);
            ItemStack[] stacks = ingredient.getItems();
            if (Arrays.stream(stacks).allMatch(is -> is.is(Tags.Items.ORES) || is.is(Tags.Items.RAW_MATERIALS))) {
                SmeltingRecipe r = (SmeltingRecipe) recipe;
                boolean found = false;
                ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
                for (var brp : blastingRecipePairs) {
                    Recipe<?> br = brp.getFirst();
                    if (sinodivination$areIngredientEquals(r.getIngredients().get(0), stacks, br.getIngredients().get(0), brp.getSecond())) {
                        found = true;
                        if (ItemStack.isSameItemSameTags(br.getResultItem(RegistryAccess.EMPTY), output)) {
                            iterator.remove();
                        }
                        break;
                    }
                }
                if (!found) {
                    BlastingRecipe newRecipe = new BlastingRecipe(id, recipe.getGroup(), CookingBookCategory.BLOCKS, ingredient, output, r.getExperience(), r.getCookingTime());
                    blasting.put(id, newRecipe);
                    iterator.remove();
                }
                count++;
            }
        }

        if (count == 0) return;

        Map<ResourceLocation, Recipe<?>> byNameMap = new HashMap<>(byName.size());
        Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> map = Maps.newHashMap();
        map.put(RecipeType.SMELTING, smelting);
        map.put(RecipeType.BLASTING, blasting);
        byNameMap.putAll(smelting);
        byNameMap.putAll(blasting);
        for (Map.Entry<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> e : recipes.entrySet()) {
            RecipeType<?> key = e.getKey();
            if (key != RecipeType.SMELTING && key != RecipeType.BLASTING) {
                Map<ResourceLocation, Recipe<?>> value = e.getValue();
                map.put(key, value);
                byNameMap.putAll(value);
            }
        }
        this.recipes = ImmutableMap.copyOf(map);
        this.byName = ImmutableMap.copyOf(byNameMap);

        sinodivination$isReplaced = true;
    }

    private boolean sinodivination$areIngredientEquals(Ingredient ingredient1, ItemStack[] stacks1, Ingredient ingredient2, ItemStack[] stacks2) {
        if (ingredient1.equals(ingredient2) || ingredient2.equals(ingredient1)) {
            return true;
        }
        for (ItemStack stack : stacks1) {
            boolean found = false;
            for (ItemStack itemStack : stacks2) {
                if (ItemStack.isSameItemSameTags(stack, itemStack)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}
