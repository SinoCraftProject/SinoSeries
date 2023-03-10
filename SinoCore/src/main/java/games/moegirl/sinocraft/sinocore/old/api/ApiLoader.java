package games.moegirl.sinocraft.sinocore.old.api;

import games.moegirl.sinocraft.sinocore.api.crafting.CraftingApi;
import games.moegirl.sinocraft.sinocore.api.network.NetworkApi;

public interface ApiLoader {

    void loadAll(String id, CraftingApi craftingApi, NetworkApi networkApi);
}
