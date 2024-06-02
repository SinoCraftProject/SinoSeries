package games.moegirl.sinocraft.sinocore.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public interface IDataGenContext {

    String getModId();

    PackOutput getOutput();

    CompletableFuture<HolderLookup.Provider> registriesFuture();

    default Object getExistingFileHelper() {
        throw new IllegalStateException("ExistingFileHelper can only get from Forge platform.");
    }
}
