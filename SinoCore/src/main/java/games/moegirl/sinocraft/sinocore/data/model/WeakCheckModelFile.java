package games.moegirl.sinocraft.sinocore.data.model;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
