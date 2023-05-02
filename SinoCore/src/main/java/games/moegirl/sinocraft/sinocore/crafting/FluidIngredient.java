package games.moegirl.sinocraft.sinocore.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * 用于流体的 Ingredient
 */
public class FluidIngredient {

    public static final FluidIngredient EMPTY = new FluidIngredient(Fluids.EMPTY, 0);

    private final Fluid fluid;
    @Nullable
    private final TagKey<Fluid> tag;

    private final int amount;

    public FluidIngredient(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.tag = null;
        this.amount = amount;
    }

    public FluidIngredient(TagKey<Fluid> tag, int amount) {
        this.fluid = Fluids.EMPTY;
        this.tag = tag;
        this.amount = amount;
    }

    public Fluid fluid() {
        return fluid;
    }

    public Optional<TagKey<Fluid>> tag() {
        return Optional.ofNullable(tag);
    }

    public int amount() {
        return amount;
    }

    public boolean test(@Nullable FluidStack stack) {
        return stack != null && stack.getAmount() >= amount &&
                (tag == null ? fluid == stack.getRawFluid() : stack.getRawFluid().defaultFluidState().is(tag));
    }

    public List<FluidStack> allStacks() {
        if (tag == null) {
            return List.of(new FluidStack(fluid, amount));
        }

        //noinspection DataFlowIssue
        return StreamSupport.stream(ForgeRegistries.FLUIDS.tags().getTag(tag).spliterator(), false)
                .map(f -> new FluidStack(f, amount))
                .toList();
    }

    /**
     * 从 {@link FriendlyByteBuf} 读取 Ingredient
     */
    public static FluidIngredient fromNetwork(FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            Fluid fluid = buffer.readRegistryId();
            int amount = buffer.readInt();
            return new FluidIngredient(fluid, amount);
        } else {
            ResourceLocation tagName = buffer.readResourceLocation();
            int amount = buffer.readInt();
            return new FluidIngredient(FluidTags.create(tagName), amount);
        }
    }

    /**
     * 从 {@link JsonObject} 读取 Ingredient
     */
    public static FluidIngredient fromJson(JsonObject json) {
        if (json.has("tag")) {
            int amount = GsonHelper.getAsInt(json, "amount", 1000);
            ResourceLocation fluidName = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
            TagKey<Fluid> tag = FluidTags.create(fluidName);
            return new FluidIngredient(tag, amount);
        } else if (json.has("fluid")) {
            int amount = GsonHelper.getAsInt(json, "amount", 1000);
            String fluidName = GsonHelper.getAsString(json, "fluid");
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
            Objects.requireNonNull(fluid, "Unknown fluid " + fluidName);
            return new FluidIngredient(fluid, amount);
        }
        return EMPTY;
    }

    public static void write(FriendlyByteBuf buffer, FluidIngredient ingredient) {
        Optional<TagKey<Fluid>> tagOptional = ingredient.tag();
        buffer.writeBoolean(tagOptional.isEmpty());
        if (tagOptional.isEmpty()) {
            buffer.writeRegistryId(ForgeRegistries.FLUIDS, ingredient.fluid());
        } else {
            buffer.writeResourceLocation(tagOptional.get().location());
        }
        buffer.writeInt(ingredient.amount());
    }

    public static JsonObject write(JsonObject object, FluidIngredient ingredient) {
        if (ingredient.tag().isPresent()) {
            object.addProperty("tag", ingredient.tag().get().location().toString());
        } else {
            object.addProperty("fluid", Objects.toString(ForgeRegistries.FLUIDS.getKey(ingredient.fluid())));
        }
        object.addProperty("amount", ingredient.amount());
        return object;
    }

}
