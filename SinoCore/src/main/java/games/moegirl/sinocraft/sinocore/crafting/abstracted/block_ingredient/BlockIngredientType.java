package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import net.minecraft.resources.ResourceLocation;

/**
 * @author luqin2007
 */
public record BlockIngredientType<T extends BlockIngredient<T>>(ResourceLocation name, Serializer<T> serializer) {
}
