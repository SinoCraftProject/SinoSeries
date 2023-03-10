package games.moegirl.sinocraft.sinocore.old.network;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.api.utility.Id;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SCNetworks {

    public static final Id ID = new Id();
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(SinoCore.MODID, "common"), () -> "1", __ -> true, __ -> true);

    public static void register() {}
}
