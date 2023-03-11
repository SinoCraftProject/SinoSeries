package games.moegirl.sinocraft.sinocore.old.data;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SCData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        if (event.includeClient()) {
            generator.addProvider(true, new SCLanguage(output, SinoCore.MODID));
        }

        if (event.includeServer()) {
        }
    }
}