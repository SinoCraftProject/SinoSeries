package games.moegirl.sinocraft.sinofoundation.crafting;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.RecipeHolder;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.crafting.bamboo_plaque.BambooPlaqueRecipe;
import games.moegirl.sinocraft.sinofoundation.crafting.bamboo_plaque.BambooPlaqueRecipeContainer;
import games.moegirl.sinocraft.sinofoundation.crafting.bamboo_plaque.BambooPlaqueRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author luqin2007
 */
public class SFDRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SinoFoundation.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SinoFoundation.MODID);

    public static final RecipeHolder<BambooPlaqueRecipeContainer, BambooPlaqueRecipe, BambooPlaqueRecipeSerializer> BAMBOO_PLAQUE_RECIPE = RecipeHolder.register(SFDBlockItems.BAMBOO_PLAQUE, BambooPlaqueRecipeSerializer.INSTANCE, SERIALIZER_REGISTRY, TYPE_REGISTRY);

    public static void register(IEventBus bus) {
        SERIALIZER_REGISTRY.register(bus);
        TYPE_REGISTRY.register(bus);
    }
}
