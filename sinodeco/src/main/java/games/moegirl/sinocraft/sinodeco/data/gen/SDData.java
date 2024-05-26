package games.moegirl.sinocraft.sinodeco.data.gen;

import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;

public class SDData {
    public static void register() {
        var register = RegistryManager.obtainDataProvider(SinoDeco.MODID);

//        var blockTagProvider = register.put(context -> new BlockTagProvider(context.getOutput(), context.registriesFuture()));
//        register.put(context -> new ItemTagProvider(context, blockTagProvider.get()));
//
//        register.put(context -> new ItemModelProvider(context, SBRItems.ITEMS));
    }
}
