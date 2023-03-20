package games.moegirl.sinocraft.sinocore.data;

import games.moegirl.sinocraft.sinocore.woodwork.Woodwork;
import games.moegirl.sinocraft.sinocore.woodwork.event.WoodworkDataHandler;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public abstract class LanguageProviderBase extends LanguageProvider {
    public LanguageProviderBase(PackOutput output, String modid, Locale locale) {
        super(output, modid, locale.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {

        translations();
    }

    public abstract void translations();
}
