package games.moegirl.sinocraft.sinocore.forge;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.forge.ForgeDataGenContextImpl;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.concurrent.CompletableFuture;

public class SinoCorePlatformImpl {

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static IDataGenContext buildDataGeneratorContext(Object object, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        if (object instanceof GatherDataEvent event) {
            return new ForgeDataGenContextImpl(event, registriesFuture);
        }
        throw new IllegalStateException("Object [" + object + "] should be an instance of GatherDataEvent. " +
                "You can get it from FabricDataGenerator.Pack#addProvider");
    }
}
