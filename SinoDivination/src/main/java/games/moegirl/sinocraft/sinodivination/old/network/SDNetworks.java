package games.moegirl.sinocraft.sinodivination.old.network;

import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.ModFiles;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class SDNetworks {

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SinoDivination.MODID, "network"), () -> "1", s -> true, s -> true);

    public static final AtomicInteger ID = new AtomicInteger(0);

    public static void register() {
        try {
            ModFiles.getFiles(SinoDivination.class)
                    .forPackage("games.moegirl.sinocraft.sinodivination.network")
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".class") && p.startsWith("Pkt"))
                    .map(SDNetworks::mapToPktClass)
                    .filter(Objects::nonNull)
                    .forEach(SDNetworks::register);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends BasePacket<T>> void register(Class<T> type) {
        String simpleName = type.getSimpleName();
        boolean onlyClient = simpleName.startsWith("PktS2C");
        boolean onlyServer = simpleName.startsWith("PktC2S");
        Optional<NetworkDirection> direction = onlyServer ? Optional.of(NetworkDirection.PLAY_TO_SERVER)
                : onlyClient ? Optional.of(NetworkDirection.PLAY_TO_CLIENT) : Optional.empty();
        Function<FriendlyByteBuf, T> constructor = Functions.constructor(type, FriendlyByteBuf.class);
        CHANNEL.registerMessage(ID.getAndIncrement(), type, BasePacket::encode, constructor, BasePacket::consume, direction);
    }

    @Nullable
    private static <T extends BasePacket<T>> Class<T> mapToPktClass(String fileName) {
        String classPath = "games.moegirl.sinocraft.sinodivination.network." + fileName.substring(0, fileName.length() - 6);
        try {
            Class<?> aClass = SDNetworks.class.getClassLoader().loadClass(classPath);
            if (BasePacket.class.isAssignableFrom(aClass)) {
                return (Class<T>) aClass;
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
