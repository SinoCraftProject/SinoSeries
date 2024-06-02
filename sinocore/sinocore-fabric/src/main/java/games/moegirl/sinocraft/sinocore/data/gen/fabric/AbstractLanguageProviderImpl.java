package games.moegirl.sinocraft.sinocore.data.gen.fabric;

import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;
import net.minecraft.data.PackOutput;

public class AbstractLanguageProviderImpl {

    public static LanguageProviderDelegateBase createDelegate(PackOutput output, String modId, String locale) {
        throw new IllegalStateException("DataProvider only for forge platform.");
    }
}
