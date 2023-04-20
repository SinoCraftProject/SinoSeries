package games.moegirl.sinocraft.sinocalligraphy.data.lang;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class SCALanguageProviderENUS extends AbstractLanguageProvider {
    public SCALanguageProviderENUS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addItem(SCAItems.BRUSH, "Chinese brush");
        addItem(SCAItems.FAN, "Unfolded fan");
        addItem(SCAItems.FAN_FOLDED, "Fan");
        addItem(SCAItems.EMPTY_XUAN_PAPER, "Empty Xuan paper");
        addItem(SCAItems.EMPTY_XUAN_PAPER_RED, "Red Xuan paper");
        addItem(SCAItems.EMPTY_XUAN_PAPER_BLACK, "Black Xuan paper");
        addItem(SCAItems.FILLED_XUAN_PAPER, "Filled Xuan paper");
        addItem(SCAItems.INK, "Ink");
        addItem(SCAItems.GOLDEN_INK, "Golden ink");

        add(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY, "Untitled drawing");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, "Author: ");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY, "Anonymous");

        add(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_1, "Legend said, TaoismDeeplake used this fan during his speech.");
        add(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_2, "He wandered the world without a sword in his hand.");
        add(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_1, "Opening this fan, Rivers and mountains rendered by light ink floats on it.");
        add(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_2, "Carrying this fan, there will be no fear of disappointing life. fears.");

        add(SCAConstants.TRANSLATE_ADVANCEMENT_SCA, "SinoCalligraphy");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_SCA_DESC, "The painting that used color to depict all walks of life, while retained black and white to render the beauty of ages. ");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING, "On a paper, eyes can be brought to life, whether they are beautiful, or ugly.");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING_DESC, "Once put a drop of ink on paper.");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_INK, "One will see it stained, suddenly, yet silently.");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_INK_DESC, "That is it, no matter how to get some ink.");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_FAN, "Once holding this fan, one will be not afraid of torture and misery.");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_FAN_DESC, "Once holding this fan, the spirit of a knightly and free man, is self-evident.");
    }
}
