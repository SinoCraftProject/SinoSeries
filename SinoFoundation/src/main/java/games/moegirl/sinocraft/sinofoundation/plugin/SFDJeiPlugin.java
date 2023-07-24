package games.moegirl.sinocraft.sinofoundation.plugin;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * @author luqin2007
 */
@JeiPlugin
public class SFDJeiPlugin implements IModPlugin {

    private static final ResourceLocation id = new ResourceLocation(SinoFoundation.MODID, "jei");

    @Override
    public ResourceLocation getPluginUid() {
        return id;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(new ItemStack(Items.PLAYER_HEAD)));
    }
}
