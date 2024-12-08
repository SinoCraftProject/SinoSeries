package games.moegirl.sinocraft.sinobrush.data.gen;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.data.gen.advancement.AdvancementProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.lang.EnUsLangProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.lang.ZhCnLangProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.lang.ZhHkLangProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.lang.ZhTwLangProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.model.ItemModelProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.recipe.RecipeProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.BlockTagProvider;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.ItemTagProvider;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;

public class SBRDataGen {
    public static void register() {
        var register = RegistryManager.obtainDataProvider(SinoBrush.MODID);

        register.put(ZhCnLangProvider::new);
        register.put(ZhHkLangProvider::new);
        register.put(ZhTwLangProvider::new);
        register.put(EnUsLangProvider::new);

        var blockTagProvider = register.put(context -> new BlockTagProvider(context.getOutput(), context.getRegistries()));
        register.put(context -> new ItemTagProvider(context, blockTagProvider.get()));

        register.put(AdvancementProvider::new);
        register.put(RecipeProvider::new);

        register.put(context -> new ItemModelProvider(context, SBRItems.ITEMS));
    }
}
