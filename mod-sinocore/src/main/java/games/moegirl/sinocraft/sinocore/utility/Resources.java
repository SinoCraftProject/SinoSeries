package games.moegirl.sinocraft.sinocore.utility;

import games.moegirl.sinocraft.sinocore.SinoCorePlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;

public class Resources {

    public static ResourceManager getResourceManager() {
        try {
            return Minecraft.getInstance().getResourceManager();
        } catch (Exception /* ClassNotFoundException */ e) {
            return SinoCorePlatform.getServer().getResourceManager();
        }
    }
}
