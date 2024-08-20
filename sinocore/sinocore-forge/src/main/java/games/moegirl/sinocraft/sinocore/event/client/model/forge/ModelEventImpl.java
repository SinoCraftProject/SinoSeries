package games.moegirl.sinocraft.sinocore.event.client.model.forge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.event.client.ModelEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.model.AfterBakeArgs;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelEventImpl {
    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        var modified = new HashMap<ResourceLocation, BakedModel>();

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
