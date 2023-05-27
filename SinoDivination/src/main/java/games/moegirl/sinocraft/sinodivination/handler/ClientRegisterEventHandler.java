package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.AltarEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.client.blockentity.AltarRenderer;
import games.moegirl.sinocraft.sinodivination.client.blockentity.KettlePotRenderer;
import games.moegirl.sinocraft.sinodivination.client.blockentity.TripodRenderer;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegisterEventHandler {

    private static final Object2IntMap<Direction> ALTAR_COLOR_MAP = new Object2IntArrayMap<>();

    static {
        ALTAR_COLOR_MAP.put(Direction.EAST, Color.CYAN.getRGB());
        ALTAR_COLOR_MAP.put(Direction.WEST, Color.WHITE.getRGB());
        ALTAR_COLOR_MAP.put(Direction.NORTH, Color.BLACK.getRGB());
        ALTAR_COLOR_MAP.put(Direction.SOUTH, Color.RED.getRGB());
        ALTAR_COLOR_MAP.defaultReturnValue(0);
    }

    @SubscribeEvent
    public static void onEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SDBlockEntities.TRIPOD.get(), TripodRenderer::new);
        event.registerBlockEntityRenderer(SDBlockEntities.ALTAR.get(), AltarRenderer::new);
        event.registerBlockEntityRenderer(SDBlockEntities.KETTLE_POT.get(), KettlePotRenderer::new);
    }

    @SubscribeEvent
    public static void onBlockColor(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((blockState, level, pos, tintIndex) -> {
            if (level == null || pos == null) return 0;
            Direction direction = level.getBlockEntity(pos, SDBlockEntities.ALTAR.get())
                    .map(AltarEntity::getDirection)
                    .orElse(Direction.UP);
            return ALTAR_COLOR_MAP.getInt(direction);
        }, SDBlocks.ALTAR.get());
    }

}
