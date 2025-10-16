package games.moegirl.sinocraft.sinocore.interfaces.injectable;

import games.moegirl.sinocraft.sinocore.client.item.ISinoClientItem;

public interface ISinoItem {
    default ISinoClientItem sino$getClientItem() {
        return null;
    }
}
