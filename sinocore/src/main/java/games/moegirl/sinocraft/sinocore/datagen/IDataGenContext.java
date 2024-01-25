package games.moegirl.sinocraft.sinocore.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.concurrent.CompletableFuture;

public interface IDataGenContext {

    String getModId();

    PackOutput getOutput();

    CompletableFuture<HolderLookup.Provider> registriesFuture();
}
