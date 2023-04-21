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

        add(SCAConstants.GUI_BRUSH_COPY_BUTTON_TOOLTIP, "Left click to copy, right click to paste");
        add(SCAConstants.GUI_BRUSH_OUTPUT_BUTTON_TOOLTIP, "Save to file");
        add(SCAConstants.GUI_BRUSH_APPLY_BUTTON_TOOLTIP, "Save canvas");
        add(SCAConstants.GUI_BRUSH_CLEAR_BUTTON_TOOLTIP, "Clear canvas");

        add(SCAConstants.GUI_MESSAGE_BRUSH_COPIED, "Successfully copied.");
        add(SCAConstants.GUI_MESSAGE_BRUSH_PASTED, "Successfully pasted.");
        add(SCAConstants.GUI_MESSAGE_BRUSH_PASTE_FAILED, "Paste failed");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_INK, "Save failed, place ink first");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_PAPER, "Save failed, place paper first");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_UNKNOWN_SCREEN, "Save failed, unknown screen!");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_SUCCESS, "Successfully saved.");

        add(SCAConstants.NARRATION_BRUSH_CANVAS, "Brush canvas");
        add(SCAConstants.NARRATION_BRUSH_TITLE_BOX, "Chinese Brush GUI");

        add(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_SUCCESS, "Drawing was saved to {0}");
        add(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_FAIL, "Save failed，{0}");

        add(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY, "Untitled drawing");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, "Author: {0}");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY, "Anonymous");
        add(SCAConstants.TRANSLATE_DRAWING_DATE_KEY, "Drawing date: %3$d/%2$d/%1$04d %4$02d:%5$02d:%6$02d");

    }
}
