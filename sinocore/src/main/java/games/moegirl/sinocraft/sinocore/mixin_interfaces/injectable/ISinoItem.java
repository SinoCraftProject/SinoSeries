package games.moegirl.sinocraft.sinocore.mixin_interfaces.injectable;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public interface ISinoItem {
    default BlockEntityWithoutLevelRenderer sino$getCustomRender() {
        return null;
    }
}
