package games.moegirl.sinocraft.sinocore.old.data;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.data.lang.LanguageProviderENUS;
import games.moegirl.sinocraft.sinocore.data.lang.LanguageProviderZHCN;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SCData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var exHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new LanguageProviderZHCN(generator, SinoCore.MODID, "zh_cn"));
            generator.addProvider(new LanguageProviderENUS(generator, SinoCore.MODID, "en_us"));
        }

        if (event.includeServer()) {
        }
    }
}