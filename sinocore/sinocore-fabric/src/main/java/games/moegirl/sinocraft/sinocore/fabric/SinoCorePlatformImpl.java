package games.moegirl.sinocraft.sinocore.fabric;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.CompletableFuture;

public class SinoCorePlatformImpl {

    static MinecraftServer SERVER;

    public static MinecraftServer getServer() {
        return SERVER;
    }

    public static IDataGenContext buildDataGeneratorContext(Object object, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
