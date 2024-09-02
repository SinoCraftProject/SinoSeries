package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

/**
 * Model file with weak check.
 */
public class WeakCheckModelFile extends ModelFile.ExistingModelFile {

    protected final boolean strict;

    protected final Function<ResourceLocation, Boolean> onNotExists;

    public WeakCheckModelFile(ResourceLocation location, ExistingFileHelper existingHelper, boolean strict,
                              Function<ResourceLocation, Boolean> onNotExists) {
        super(location, existingHelper);

        this.strict = strict;
        this.onNotExists = onNotExists;
    }

    @Override
    protected boolean exists() {
        var result = super.exists();
        if (strict && !result) {
            return false;
        } else {
            return onNotExists.apply(getUncheckedLocation());
        }
    }
}
