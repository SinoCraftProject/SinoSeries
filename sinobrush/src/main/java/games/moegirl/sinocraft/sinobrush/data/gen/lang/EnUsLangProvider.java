package games.moegirl.sinocraft.sinobrush.data.gen.lang;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;

public class EnUsLangProvider extends AbstractLanguageProvider {
    public EnUsLangProvider(IDataGenContext context) {
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

        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN, "展此扇，上有淡墨河山");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN, "携此扇，无惧此生风雨");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_WROTE, "一把折扇");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_WROTE, "一把折扇，上面写着一些字");

        delegate.add(SBRConstants.Translation.DESCRIPTION_ITEM_COLORED, "Dyed (%1$s,%2$s,%3$s)");
        delegate.add(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, "Expended %1$s times");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FILLED_XUAN_PAPER_SIZE, "Width: %1$s, Height: %2$s");

        delegate.add(SBRConstants.Translation.HUD_FAN_PREFIX, "对方的折扇上写着：");

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

        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_NAME, "丹青描绘浮生，云墨书尽芳华");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_DESC, "欢迎游玩《华夏云墨》");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME, "纸上描眉目，不辨妍或媸");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC, "在纸上着墨");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_NAME, "踏遍山海");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_DESC, "传说，说书人深湖在 TeaCon 演讲时就用过这把扇子\n他浪迹天地，无长剑在手\n三寸惊堂木，将故事传颂");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME, "侠肝义胆");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC, "此展折扇，此任平生不惧风与雪");

        delegate.addItemTag(SBRItemTags.FAN, "Folding Fan");
        delegate.addItemTag(SBRItemTags.XUAN_PAPER, "Xuan Paper");
    }
}
