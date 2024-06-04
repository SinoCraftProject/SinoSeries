package games.moegirl.sinocraft.sinodeco.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.ForgeProvider;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.data.gen.lang.EnUsLangProvider;
import games.moegirl.sinocraft.sinodeco.data.gen.lang.ZhCnLangProvider;
import games.moegirl.sinocraft.sinodeco.data.gen.model.ItemModelProvider;

public class SDData {
    public static void register() {
        var register = RegistryManager.obtainDataProvider(SinoDeco.MODID);

        register.put(ForgeProvider::new);
        register.put(ItemModelProvider::new);

        register.put(ZhCnLangProvider::new);
        register.put(EnUsLangProvider::new);
    }
}
