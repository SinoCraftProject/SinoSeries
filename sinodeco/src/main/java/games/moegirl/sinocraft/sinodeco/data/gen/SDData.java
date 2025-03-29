package games.moegirl.sinocraft.sinodeco.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.DataGeneratorManager;
import games.moegirl.sinocraft.sinocore.data.gen.ForgeProvider;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import games.moegirl.sinocraft.sinodeco.data.gen.lang.EnUsLangProvider;
import games.moegirl.sinocraft.sinodeco.data.gen.lang.ZhCnLangProvider;
import games.moegirl.sinocraft.sinodeco.data.gen.model.ItemModelProvider;
import games.moegirl.sinocraft.sinodeco.data.gen.tag.BlockTagsProvider;
import games.moegirl.sinocraft.sinodeco.data.gen.tag.ItemTagsProvider;

public class SDData {
    public static void register() {
        DataGeneratorManager.createDataGenerator(SinoDeco.MODID, (gen) -> {
            gen.put(ForgeProvider::new);
            gen.put(ItemModelProvider::new);

            var blockProvider = gen.put(BlockTagsProvider::new);
            gen.put(context -> new ItemTagsProvider(context, blockProvider.get()));
            gen.put(RecipeProvider::new);

            gen.put(ZhCnLangProvider::new);
            gen.put(EnUsLangProvider::new);
        });
    }
}
