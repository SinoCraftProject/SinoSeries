package games.moegirl.sinocraft.sinocore.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.neoforge.NeoForgeDataGenContextImpl;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.concurrent.CompletableFuture;

public class SinoCorePlatformImpl {

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static IDataGenContext buildDataGeneratorContext(Object object, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        if (object instanceof GatherDataEvent event) {
            return new NeoForgeDataGenContextImpl(event, registriesFuture);
        }
        throw new IllegalStateException("Object [" + object + "] should be an instance of GatherDataEvent. " +
                "You can get it from FabricDataGenerator.Pack#addProvider");
    }

    public static boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}
