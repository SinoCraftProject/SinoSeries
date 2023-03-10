package games.moegirl.sinocraft.sinocore.old.handler;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.api.utility.ModFiles;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * All other register events
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEventHandler {

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        try {
            ModFiles.getFiles(SinoCore.MODID)
                    .forPackage("games.moegirl.sinocraft.sinocore.api.capability", 1)
                    .filter(Files::isRegularFile)
                    .map(f -> f.getFileName().toString())
                    .filter(fn -> fn.startsWith("I"))
                    .filter(fn -> fn.endsWith(".class"))
                    .map(fn -> fn.substring(1, fn.length() - 6))
                    .forEach(n -> registerCapability(n, event));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void registerCapability(String className, RegisterCapabilitiesEvent event) {
        try {
            String classPath = "games.moegirl.sinocraft.sinocore.api.capability." + className;
            Class<?> aClass = RegisterEventHandler.class.getClassLoader().loadClass(classPath);
            event.register(aClass);
        } catch (Exception ignored) {
        }
    }
}
