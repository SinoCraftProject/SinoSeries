package games.moegirl.sinocraft.sinocore.event.client.model.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.event.client.ModelEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.model.AfterBakeArgs;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.HashMap;
import java.util.Objects;

@EventBusSubscriber(modid = SinoCore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelEventImpl {
    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        var modified = new HashMap<ModelResourceLocation, BakedModel>();

        for (var entry : event.getModels().entrySet()) {
            var id = entry.getKey();
            var model = entry.getValue();
            var newModel = ModelEvents.AFTER_BAKE.invoke(new AfterBakeArgs(event.getModelBakery(), id, model)).model();
            if (!Objects.equals(newModel, model)) {
                modified.put(id, newModel);
            }
        }

        for (var entry : modified.entrySet()) {
            event.getModels().replace(entry.getKey(), entry.getValue());
        }
    }
}
