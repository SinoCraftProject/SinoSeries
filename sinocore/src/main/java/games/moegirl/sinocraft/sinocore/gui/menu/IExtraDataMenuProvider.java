package games.moegirl.sinocraft.sinocore.gui.menu;

import net.minecraft.network.FriendlyByteBuf;

public interface IExtraDataMenuProvider extends ISimpleMenuProvider {
    void writeExtraData(FriendlyByteBuf buf);
}
