package games.moegirl.sinocraft.sinocore.old.network;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.concurrent.atomic.AtomicInteger;

public class SCNetworks {

    public static final AtomicInteger ID = new AtomicInteger();
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(SinoCore.MODID, "common"), () -> "1", __ -> true, __ -> true);

    public static void register() {}
}
