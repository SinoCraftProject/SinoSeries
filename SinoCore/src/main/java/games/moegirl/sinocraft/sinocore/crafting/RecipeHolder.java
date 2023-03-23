package games.moegirl.sinocraft.sinocore.crafting;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 用于保存注册某个配方类型的注册结果
 *
 * @param <C> Container 类型
 * @param <T> Recipe 类型
 * @param <S> Serializer 类型
 * @author luqin2007
 */
public final class RecipeHolder<C extends Container, T extends Recipe<C>, S extends AbstractRecipeSerializer<T>> {

    public static <C extends Container, T extends Recipe<C>, S extends AbstractRecipeSerializer<T>> RecipeHolder<C, T, S> register(
            DeferredRegister<RecipeSerializer<?>> registry, RegistryObject<? extends ItemLike> sign, S serializer) {
        ResourceLocation id = sign.getId();
        RecipeHolder<C, T, S> holder = new RecipeHolder<>(id, Suppliers.memoize(() -> sign.get().asItem()), serializer, serializer.recipeClass());
        registry.register(id.getPath(), () -> {
            holder.recipeType = RecipeType.register(id.toString());
            return serializer;
        });
        return holder;
    }

    private final ResourceLocation name;
    private final Supplier<? extends Item> sign;
    private RecipeType<T> recipeType;
    private final S serializer;
    private final Class<T> type;

    public RecipeHolder(ResourceLocation name, Supplier<? extends Item> sign, S serializer, Class<T> type) {
        this.name = name;
        this.sign = sign;
        this.serializer = serializer;
        this.type = type;
    }

    public Optional<T> match(Level level, C container) {
        return level.getRecipeManager().getRecipeFor(recipeType, container, level);
    }

    public Optional<ItemStack> matchResult(Level level, C container) {
        return match(level, container).map(recipe -> recipe.assemble(container));
    }

    public ResourceLocation name() {
        return name;
    }

    public Supplier<? extends Item> sign() {
        return sign;
    }

    public RecipeType<T> recipeType() {
        Objects.requireNonNull(recipeType, "get RecipeType must after RegisterEvent");
        return recipeType;
    }

    public S serializer() {
        return serializer;
    }

    public Class<T> type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        return Objects.equals(this.name, ((RecipeHolder<?, ?, ?>) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
