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
import games.moegirl.sinocraft.sinocore.data.gen.DataGeneratorManager;

public class SBRDataGen {
    public static void register() {
        DataGeneratorManager.createDataGenerator(SinoBrush.MODID, gen -> {
            gen.put(ZhCnLangProvider::new);
            gen.put(ZhHkLangProvider::new);
            gen.put(ZhTwLangProvider::new);
            gen.put(EnUsLangProvider::new);

            var blockTagProvider = gen.put(context -> new BlockTagProvider(context.getOutput(), context.getRegistries()));
            gen.put(context -> new ItemTagProvider(context, blockTagProvider.get()));

            gen.put(AdvancementProvider::new);
            gen.put(RecipeProvider::new);

            gen.put(context -> new ItemModelProvider(context, SBRItems.ITEMS));
        });

    }
}
