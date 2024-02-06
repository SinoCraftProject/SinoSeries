package games.moegirl.sinocraft.sinobrush.data;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.data.lang.ZhCnLangProvider;
import games.moegirl.sinocraft.sinobrush.data.model.ItemModelProvider;
import games.moegirl.sinocraft.sinobrush.data.tag.BlockTagProvider;
import games.moegirl.sinocraft.sinobrush.data.tag.ItemTagProvider;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;

public class SBRDataGen {
    public static void register() {
        var register = RegistryManager.obtainDataProvider(SinoBrush.MODID);

        register.put(ZhCnLangProvider::new);

        var blockTagProvider = register.put(context -> new BlockTagProvider(context.getOutput(), context.registriesFuture()));
        register.put(context -> new ItemTagProvider(context, blockTagProvider.get()));

        register.put(context -> new ItemModelProvider(context, SBRItems.ITEMS));
    }
}
