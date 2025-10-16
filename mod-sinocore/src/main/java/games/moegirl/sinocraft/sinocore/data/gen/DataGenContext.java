package games.moegirl.sinocraft.sinocore.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class DataGenContext {

    private final String modId;
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final DataGenerator generator;

    public DataGenContext(String modId, DataGenerator generator, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.modId = modId;
        this.generator = generator;
        this.output = output;
        this.registries = registries;
    }

    public String getModId() {
        return modId;
    }

    public PackOutput getOutput() {
        return output;
    }

    public CompletableFuture<HolderLookup.Provider> getRegistries() {
        return registries;
    }

    public DataGenerator getGenerator() {
        return generator;
    }

    public Object getExistingFileHelper() {
        throw new IllegalStateException("ExistingFileHelper can only get from Forge platform.");
    }
}
