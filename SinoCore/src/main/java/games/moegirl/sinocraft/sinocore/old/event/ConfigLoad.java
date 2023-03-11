package games.moegirl.sinocraft.sinocore.old.event;

import com.electronwill.nightconfig.core.CommentedConfig;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigLoad {

    @SubscribeEvent
    public static void onConfigLoading(ModConfigEvent.Loading event) {
        var config = event.getConfig().getConfigData();
        load(config);
    }

    @SubscribeEvent
    public static void onConfigReloading(ModConfigEvent.Reloading event) {
        var config = event.getConfig().getConfigData();
        load(config);
    }

    public static void load(CommentedConfig config) {
//        QuizConstants.URL = config.getOrElse("quiz.url",
//                "https://quiz.sino.moegirl.games/api/quiz/something");
    }
}
