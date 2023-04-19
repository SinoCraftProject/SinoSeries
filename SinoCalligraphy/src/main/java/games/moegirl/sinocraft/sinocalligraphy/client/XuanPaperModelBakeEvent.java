package games.moegirl.sinocraft.sinocalligraphy.client;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.client.ReplacedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

/**
 * Client render event subscriber.
 */
@Mod.EventBusSubscriber(modid = SinoCalligraphy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class XuanPaperModelBakeEvent {

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        var registry = event.getModelBakery();
        ModelResourceLocation location = new ModelResourceLocation(SCAItems.FILLED_XUAN_PAPER.getId(), "inventory");
        var model = Objects.requireNonNull(registry.getBakedTopLevelModels().get(location), location + " has no existing model");
        registry.getBakedTopLevelModels().put(location, new ReplacedModel(model, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, ItemDisplayContext.FIRST_PERSON_LEFT_HAND, ItemDisplayContext.FIXED));
    }
}
