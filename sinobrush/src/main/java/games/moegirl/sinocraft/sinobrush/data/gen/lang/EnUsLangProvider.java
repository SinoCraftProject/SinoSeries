package games.moegirl.sinocraft.sinobrush.data.gen.lang;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;

public class EnUsLangProvider extends AbstractLanguageProvider {
    public EnUsLangProvider(DataGenContext context) {
        super(context, "en_us");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addItem(SBRItems.FAN, "Folding Fan (Unfolded)");
        delegate.addItem(SBRItems.FOLDED_FAN, "Folding Fan (Folded)");
        delegate.addItem(SBRItems.XUAN_PAPER, "Xuan Paper");
        delegate.addItem(SBRItems.FILLED_XUAN_PAPER, "Filled Xuan Paper");
        delegate.addItem(SBRItems.INK_BOTTLE, "Ink Bottle");
        delegate.addItem(SBRItems.BRUSH, "Chinese Writing Brush");

        delegate.addCustomStat(SBRStats.DRAW_BY_BRUSH, "Times of writing with brush");
        delegate.addCustomStat(SBRStats.UNFOLD_FAN, "Times of unfolding the fan");

        delegate.addTab(SBRItems.SINO_BRUSH_TAB, "SinoBrush");

        delegate.add(SBRConstants.Translation.DRAWING_TITLE_LABEL, "Calligraphy ");
        delegate.add(SBRConstants.Translation.DRAWING_TITLE_UNKNOWN, "Untitled");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_LABEL, "Author: ");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_UNKNOWN, "Anonymous");
        delegate.add(SBRConstants.Translation.DRAWING_DATE_LABEL, "Date of Completion %2$s/%3$s/%1$s %4$s:%5$s:%6$s");

        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_1, "Unfold this fan, and mountains and rivers emerge in soft ink.");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_2, "Carry this fan, and fear no storms of life.");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_EMPTY, "A folding fan.");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_WROTE, "A folding fan, inscribed with some words: %s");

        delegate.add(SBRConstants.Translation.DESCRIPTION_ITEM_COLORED, "Dyed (%1$s,%2$s,%3$s)");
        delegate.add(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, "Expended %1$s times");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FILLED_XUAN_PAPER_SIZE, "Width: %1$s, Height: %2$s");

        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_SAVE, "Save the canvas to an image");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_COPY, "Left click to copy, right click to paste");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_BRUSH, "Sign and name");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_CLEAR, "Clear the canvas");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_HINT_NAME, "Naming the calligraphy");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_COPIED, "Copied canvas to clipboard");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_PASTED, "Read the canvas from clipboard");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_SAVED, "The image was saved to %1$s");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_SUCCESSFUL, "Signed");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_INK, "Sign failed: No ink bottle provided");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_PAPER, "Sign failed: No paper provided");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_OUTPUT_OCCUPIED, "Sign failed: Take the previous calligraphy first");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_BRUSH_ON_HAND, "Sign failed: Why you want write without a writing brush?");
        delegate.add(SBRConstants.Translation.GUI_FAN_SETTING_HUD_POSITION, "Setting HUD position");
        delegate.add(SBRConstants.Translation.GUI_FAN_SETTING_HUD_HINT, "Drag to set position, scroll to set size.");

        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_NAME, "SinoBrush");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_DESC, "Painting Lives by Brush, Writing Elegance in Ink.");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME, "Brushing Grace Upon Paper, no matter Fair or Faint");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC, "Let ink dance upon the canvas of paper");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_NAME, "Traversing Mountains and Seas");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_DESC, "Legend has it that the storyteller Taoism Deeplake once wielded this very fan at TeaCon.\nHe roamed the world, no sword in hand,\nAlone, he carried his tales across time.");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME, "Chivalrous Heart and Righteous Spirit");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC, "With this folding fan in hand,\nI shall face neither wind nor snow with fear.");

        delegate.addItemTag(SBRItemTags.FAN, "Folding Fan");
        delegate.addItemTag(SBRItemTags.XUAN_PAPER, "Xuan Paper");
    }
}
