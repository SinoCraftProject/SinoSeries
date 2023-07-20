package games.moegirl.sinocraft.sinocore.data.gen.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

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
    public ItemModelBuilder texture(String key, ResourceLocation texture) {
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
