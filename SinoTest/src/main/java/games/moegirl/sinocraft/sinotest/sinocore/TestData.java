package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoTest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();

        generator.addProvider(true, new TestZhCnLanguageProvider(output, SinoTest.MODID, "zh_cn"));
    }
}
