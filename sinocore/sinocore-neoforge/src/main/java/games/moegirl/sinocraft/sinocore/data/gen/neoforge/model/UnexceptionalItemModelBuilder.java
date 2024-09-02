package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author luqin2007
 */
public class UnexceptionalItemModelBuilder extends ItemModelBuilder {

    final List<Pair<String, ResourceLocation>> notExistingTexture = new ArrayList<>();

    public UnexceptionalItemModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
    }

    @Override
    public @NotNull ItemModelBuilder texture(@NotNull String key, @NotNull ResourceLocation texture) {
        try {
            return super.texture(key, texture);
        } catch (IllegalArgumentException e) {
            notExistingTexture.add(Pair.of(key, texture));
            textures.put(key, texture.toString());
            return this;
        }
    }

    public boolean isEmpty() {
        return notExistingTexture.isEmpty();
    }

    public void forEach(Consumer<? super Pair<String, ResourceLocation>> action) {
        notExistingTexture.forEach(action);
    }
}
