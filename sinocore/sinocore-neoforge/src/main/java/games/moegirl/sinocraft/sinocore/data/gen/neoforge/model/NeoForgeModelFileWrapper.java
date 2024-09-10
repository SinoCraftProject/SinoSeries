package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.utility.ISelf;
import games.moegirl.sinocraft.sinocore.utility.IWrapper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.NotNull;

public class NeoForgeModelFileWrapper<F extends ModelFile, T extends NeoForgeModelFileWrapper<F, T>>
        implements IModelFile, IWrapper<F, T> {

    protected final F modelFile;

    public NeoForgeModelFileWrapper(F modelFile) {
        this.modelFile = modelFile;
    }

    @Override
    public ResourceLocation getLocation() {
        return modelFile.getLocation();
    }

    @Override
    public ResourceLocation getUncheckedLocation() {
        return modelFile.getUncheckedLocation();
    }

    @Override
    public @NotNull F getOrigin() {
        return modelFile;
    }

    @Override
    public @NotNull ISelf<? extends T> newWrapper(F object) {
        return new NeoForgeModelFileWrapper<>(object);
    }
}
