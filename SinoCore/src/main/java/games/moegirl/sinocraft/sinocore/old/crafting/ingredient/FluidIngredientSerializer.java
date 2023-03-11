package games.moegirl.sinocraft.sinocore.old.crafting.ingredient;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Optional;

/**
 * Fluid ingredient serializer
 */
public enum FluidIngredientSerializer {
    INSTANCE;

    public FluidIngredient fromNetwork(FriendlyByteBuf buffer) {
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

    public FluidIngredient fromJson(JsonObject json) {
        int amount = GsonHelper.getAsInt(json, "amount", 1000);
        if (json.has("tag")) {
            ResourceLocation fluidName = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
            TagKey<Fluid> tag = FluidTags.create(fluidName);
            return new FluidIngredient(tag, amount);
        } else if (json.has("fluid")) {
            String fluidName = GsonHelper.getAsString(json, "fluid");
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
            Objects.requireNonNull(fluid, "Unknown fluid " + fluidName);
            return new FluidIngredient(fluid, amount);
        } else {
            return FluidIngredient.EMPTY;
        }
    }

    public void write(FriendlyByteBuf buffer, FluidIngredient ingredient) {
        Optional<TagKey<Fluid>> tagOptional = ingredient.tag();
        buffer.writeBoolean(tagOptional.isEmpty());
        if (tagOptional.isEmpty()) {
            buffer.writeRegistryId(ForgeRegistries.FLUIDS, ingredient.fluid());
        } else {
            buffer.writeResourceLocation(tagOptional.get().location());
        }
        buffer.writeInt(ingredient.amount());
    }

    public JsonObject write(JsonObject object, FluidIngredient ingredient) {
        if (ingredient.tag().isPresent()) {
            object.addProperty("tag", ingredient.tag().get().location().toString());
        } else {
            object.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(ingredient.fluid()).toString());
        }
        object.addProperty("amount", ingredient.amount());
        return object;
    }
}