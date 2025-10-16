package games.moegirl.sinocraft.sinocore.event.client.model.fabric;

import games.moegirl.sinocraft.sinocore.event.client.ModelEvents;
import games.moegirl.sinocraft.sinocore.event.client.args.model.AfterBakeArgs;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class ModelEventImpl implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelAfterBake().register(((model, context) ->
                ModelEvents.AFTER_BAKE.invoke(new AfterBakeArgs(context.loader(), context.topLevelId(), model)).model()));
    }
}
