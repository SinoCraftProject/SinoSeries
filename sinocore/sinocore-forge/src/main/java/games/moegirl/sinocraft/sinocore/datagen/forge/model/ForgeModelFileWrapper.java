package games.moegirl.sinocraft.sinocore.datagen.forge.model;

import games.moegirl.sinocraft.sinocore.datagen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.util.ISelf;
import games.moegirl.sinocraft.sinocore.util.IWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;

public class ForgeModelFileWrapper<F extends ModelFile, T extends ForgeModelFileWrapper<F, T>>
        implements IModelFile, IWrapper<F, T> {

    protected final F modelFile;

    public ForgeModelFileWrapper(F modelFile) {
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
    public F getOrigin() {
        return modelFile;
    }

    @Override
    public ISelf<? extends T> newWrapper(F object) {
        return new ForgeModelFileWrapper<>(object);
    }
}
