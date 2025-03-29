package games.moegirl.sinocraft.sinocore.client.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public interface ISinoClientItem {
    default BlockEntityWithoutLevelRenderer sino$getCustomRender() {
        return null;
    }
}
