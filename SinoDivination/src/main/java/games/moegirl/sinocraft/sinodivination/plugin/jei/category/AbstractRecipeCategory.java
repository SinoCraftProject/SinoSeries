package games.moegirl.sinocraft.sinodivination.plugin.jei.category;

import games.moegirl.sinocraft.sinocore.crafting.recipe.RecipeHolder;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractRecipeCategory<T extends Recipe<?>> implements IRecipeCategory<T> {

    protected final ResourceLocation id;
    protected final RecipeType<T> type;
    protected final Component name;
    protected final IDrawable icon, background;

    public AbstractRecipeCategory(IRecipeCategoryRegistration registration, RecipeType<T> type, RecipeHolder<?, T, ?> mcType, String name) {
        this.id = mcType.name();
        this.type = type;
        this.name = Component.translatable(name);

        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(mcType.sign().get()));
        this.background = buildBackground(guiHelper);
    }

    protected abstract IDrawable buildBackground(IGuiHelper guiHelper);

    @Override
    public Component getTitle() {
        return name;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return type;
    }

    protected ResourceLocation texture(String... path) {
        return new ResourceLocation(id.getNamespace(), "textures/" + String.join("/", path) + ".png");
    }
}
