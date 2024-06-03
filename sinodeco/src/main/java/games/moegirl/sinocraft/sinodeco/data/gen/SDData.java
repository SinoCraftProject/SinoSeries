package games.moegirl.sinocraft.sinodeco.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.ForgeProvider;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.block.item.SDBlockItems;
import games.moegirl.sinocraft.sinodeco.data.gen.model.SDItemModelProvider;
import games.moegirl.sinocraft.sinodeco.item.SDItems;

public class SDData {
    public static void register() {
        var register = RegistryManager.obtainDataProvider(SinoDeco.MODID);
        register.put(ForgeProvider::new);
        register.put(context -> new SDItemModelProvider(context, SDBlockItems.ITEMS, SDItems.ITEMS));
    }
}
