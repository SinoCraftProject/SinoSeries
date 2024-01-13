package games.moegirl.sinocraft.sinocore.handler;

import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// 测试打断点用
@Mod.EventBusSubscriber
public class LevelEventHandler {

    @SubscribeEvent
    public static void onLevelSave(LevelEvent.Save event) {
        System.out.println("Saved");
    }
}
