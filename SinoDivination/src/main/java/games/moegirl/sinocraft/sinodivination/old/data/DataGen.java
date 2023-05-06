package games.moegirl.sinocraft.sinodivination.old.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.data.provider.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SinoDivination.MODID)
public class DataGen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            ExistingFileHelper fileHelper = event.getExistingFileHelper();
            SDBlocks.COTINUS_CHEST.get().verifyTexture(fileHelper);
            SDBlocks.SOPHORA_CHEST.get().verifyTexture(fileHelper);
            SDBlocks.JUJUBE_CHEST.get().verifyTexture(fileHelper);

            //block/item models, blockstate JSONs, language files, etc.
            event.getGenerator().addProvider(true, new SDBlockStateProvider(event));
            event.getGenerator().addProvider(true, new SDItemModelProvider(event));
            event.getGenerator().addProvider(true, new SDEnLanguageProvider(event.getGenerator()));
            event.getGenerator().addProvider(true, new SDZhLanguageProvider(event.getGenerator()));
            event.getGenerator().addProvider(true, new SDLootTableProvider(event.getGenerator()));
            event.getGenerator().addProvider(true, new SDLzhLanguageProvider(event));
        }
        if (event.includeServer()) {
            SDBlockTagProvider provider;
            //recipes, advancements, tags, etc.
            event.getGenerator().addProvider(true, new SDRecipeProvider(event.getGenerator()));
            event.getGenerator().addProvider(true, provider = new SDBlockTagProvider(event));
            event.getGenerator().addProvider(true, new SDItemTagProvider(event, provider));
        }
    }
}
