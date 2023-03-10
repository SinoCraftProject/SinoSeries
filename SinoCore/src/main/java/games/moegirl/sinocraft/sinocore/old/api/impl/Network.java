package games.moegirl.sinocraft.sinocore.old.api.impl;

import games.moegirl.sinocraft.sinocore.api.network.NetworkApi;
import games.moegirl.sinocraft.sinocore.api.utility.Id;
import games.moegirl.sinocraft.sinocore.network.SCNetworks;
import net.minecraftforge.network.simple.SimpleChannel;

public enum Network implements NetworkApi {

    INSTANCE;

    @Override
    public SimpleChannel channel() {
        return SCNetworks.CHANNEL;
    }

    @Override
    public Id id() {
        return SCNetworks.ID;
    }
}
