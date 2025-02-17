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
        var register = DataGeneratorManager.createDataGenerator(SinoDeco.MODID);

        register.put(ForgeProvider::new);
        register.put(ItemModelProvider::new);

        var blockProvider = register.put(BlockTagsProvider::new);
        register.put(context -> new ItemTagsProvider(context, blockProvider.get()));
        register.put(RecipeProvider::new);

        register.put(ZhCnLangProvider::new);
        register.put(EnUsLangProvider::new);
    }
}
