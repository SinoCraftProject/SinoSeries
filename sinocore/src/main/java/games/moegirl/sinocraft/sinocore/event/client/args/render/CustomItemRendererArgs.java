package games.moegirl.sinocraft.sinocore.event.client.args.render;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;

public record CustomItemRendererArgs(RendererRegister register) {

    public interface RendererRegister {
        void register(BlockEntityWithoutLevelRenderer renderer, Item... items);
    }
}
