package games.moegirl.sinocraft.sinofeast.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofeast.SFConstants;
import net.minecraft.data.PackOutput;

public class SFLanguageProviderENUS extends AbstractLanguageProvider {
    public SFLanguageProviderENUS(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    public void translate() {
        add(SFConstants.TRANSLATE_TASTE_EMPTY, "Empty");
        add(SFConstants.TRANSLATE_TASTE_SOUR, "Sour");
        add(SFConstants.TRANSLATE_TASTE_SWEET, "Sweet");
        add(SFConstants.TRANSLATE_TASTE_BITTER, "Bitter");
        add(SFConstants.TRANSLATE_TASTE_SPICY, "Spicy");
        add(SFConstants.TRANSLATE_TASTE_SALTY, "Salty");
        add(SFConstants.TRANSLATE_TASTE_PUNGENT, "Pungent");
        add(SFConstants.TRANSLATE_TASTE_ACRID, "Acrid");
        add(SFConstants.TRANSLATE_TASTE_FRESH, "Fresh");

        add(SFConstants.TRANSLATE_PREFER_TOOLTIP, "You prefer this food.");
        add(SFConstants.TRANSLATE_LIKE_TOOLTIP, "You like this food.");
        add(SFConstants.TRANSLATE_DISLIKE_TOOLTIP, "You dislike this food.");
    }
}
