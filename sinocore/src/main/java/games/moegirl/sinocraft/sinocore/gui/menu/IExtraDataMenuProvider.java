package games.moegirl.sinocraft.sinocore.gui.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;

public interface IExtraDataMenuProvider extends ISimpleMenuProvider {
    void writeExtraData(RegistryFriendlyByteBuf buf);
}
