package games.moegirl.sinocraft.sinocore.crafting.abstracted;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipe;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipeContainer;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SCRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SinoCore.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SinoCore.MODID);

    public static final RecipeHolder<BlockInteractRecipeContainer, BlockInteractRecipe, BlockInteractRecipeSerializer> BLOCK_INTERACT_RECIPE = RecipeHolder.register(new ResourceLocation(SinoCore.MODID, "block_interacting"), Items.PLAYER_HEAD, BlockInteractRecipeSerializer.INSTANCE, SERIALIZER_REGISTRY, TYPE_REGISTRY);

    public static void register(IEventBus bus) {
        SERIALIZER_REGISTRY.register(bus);
        TYPE_REGISTRY.register(bus);
    }
}
