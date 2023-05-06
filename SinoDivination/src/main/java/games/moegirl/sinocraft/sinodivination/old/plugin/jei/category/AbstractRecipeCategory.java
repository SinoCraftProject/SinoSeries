package games.moegirl.sinocraft.sinodivination.old.plugin.jei.category;

import net.minecraft.world.item.crafting.Recipe;

// TODO jei support
public abstract class AbstractRecipeCategory<T extends Recipe<?>> /*implements IRecipeCategory<T>*/ {
//
//    protected final ResourceLocation id;
//    protected final RecipeType<T> type;
//    protected final Component name;
//    protected final IDrawable icon, background;
//
//    public AbstractRecipeCategory(IRecipeCategoryRegistration registration, RecipeType<T> type, RecipeHolder<?, T, ?> mcType, String name) {
//        this.id = mcType.name();
//        this.type = type;
//        this.name = new TranslatableComponent(name);
//
//        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
//        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(mcType.sign().get()));
//        this.background = buildBackground(guiHelper);
//    }
//
//    protected abstract IDrawable buildBackground(IGuiHelper guiHelper);
//
//    @Override
//    public Component getTitle() {
//        return name;
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }
//
//    @Override
//    public IDrawable getIcon() {
//        return icon;
//    }
//
//    @Override
//    public ResourceLocation getUid() {
//        return type.getUid();
//    }
//
//    @Override
//    public Class<? extends T> getRecipeClass() {
//        return type.getRecipeClass();
//    }
//
//    @Override
//    public RecipeType<T> getRecipeType() {
//        return type;
//    }
//
//    protected ResourceLocation texture(String... path) {
//        return new ResourceLocation(id.getNamespace(), "textures/" + String.join("/", path) + ".png");
//    }
}
