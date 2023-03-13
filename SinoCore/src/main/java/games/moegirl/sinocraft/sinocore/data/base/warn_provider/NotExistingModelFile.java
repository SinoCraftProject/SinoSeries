package games.moegirl.sinocraft.sinocore.data.base.warn_provider;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;

public class NotExistingModelFile extends ModelFile.ExistingModelFile {

    public static ModelFile.ExistingModelFile notExisting(ModelProvider<?> provider, ResourceLocation path, String folder,
                                                          List<Pair<ResourceLocation, ResourceLocation>> errModels) {
        ResourceLocation extended = path.getPath().contains("/") ? path
                : new ResourceLocation(path.getNamespace(), folder + "/" + path.getPath());
        errModels.add(Pair.of(path, extended));
        return new NotExistingModelFile(extended, provider.existingFileHelper);
    }

    public NotExistingModelFile(ResourceLocation location, ExistingFileHelper existingHelper) {
        super(location, existingHelper);
    }

    @Override
    public void assertExistence() {

    }
}
