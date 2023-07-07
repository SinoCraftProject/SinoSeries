package games.moegirl.sinocraft.sinocalligraphy.data.lang;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.block.SCABlocks;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.lang.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class SCALanguageProviderZHTW extends AbstractLanguageProvider {
    public SCALanguageProviderZHTW(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addItem(SCAItems.BRUSH, "毛筆");
        addItem(SCAItems.FAN, "展開的折扇");
        addItem(SCAItems.FAN_FOLDED, "折扇");
        addItem(SCAItems.EMPTY_XUAN_PAPER, "宣紙");
        addItem(SCAItems.EMPTY_XUAN_PAPER_RED, "紅色的宣紙");
        addItem(SCAItems.EMPTY_XUAN_PAPER_BLACK, "黑色的宣紙");
        addItem(SCAItems.FILLED_XUAN_PAPER, "填充過的宣紙");
        addItem(SCAItems.INK, "墨汁");
        addItem(SCAItems.GOLDEN_INK, "加金粉的墨汁");
        addItem(SCAItems.GREEN_SANDALWOOD_BARK, "青檀樹皮");
        addItem(SCAItems.WOOD_PULP_BUCKET, "木漿桶");

        addBlock(SCABlocks.PAPER_DRYING_RACK, "晾紙架");
        addBlock(SCABlocks.WOOD_PULP_BLOCK, "木漿");

        add(SCAConstants.DESCRIPTION_ID_WOOD_PULP, "木漿");

        add(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_1, "傳說，道家深湖在演講時就用過這把扇子");
        add(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_2, "他浪跡天地，無長劍在手");
        add(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_1, "開此扇，上有淡墨河山");
        add(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_2, "攜此扇，無懼此生風雨");

        add(SCAConstants.TRANSLATE_ADVANCEMENT_SCA, "華夏丹青");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_SCA_DESC, "丹青描繪浮生，雲墨書盡芳華");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING, "紙上描眉目，不辨妍或媸");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING_DESC, "在紙上著墨");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_INK, "墨染，驀然，默然");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_INK_DESC, "獲得一些墨汁");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_FAN, "此展折扇，此任平生不懼風與雪");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_FAN_DESC, "此身灑脫，此間俠義何需他人說");

        add(SCAConstants.GUI_BRUSH_COPY_BUTTON_TOOLTIP, "左鍵複製，右鍵粘貼");
        add(SCAConstants.GUI_BRUSH_OUTPUT_BUTTON_TOOLTIP, "保存到文件");
        add(SCAConstants.GUI_BRUSH_APPLY_BUTTON_TOOLTIP, "保存");
        add(SCAConstants.GUI_BRUSH_CLEAR_BUTTON_TOOLTIP, "清空畫布");

        add(SCAConstants.GUI_MESSAGE_BRUSH_COPIED, "複製成功");
        add(SCAConstants.GUI_MESSAGE_BRUSH_PASTED, "粘貼成功");
        add(SCAConstants.GUI_MESSAGE_BRUSH_PASTE_FAILED, "粘貼失敗");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_INK, "保存失敗，請放入墨汁");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_PAPER, "保存失敗，請放入宣紙");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_UNKNOWN_SCREEN, "保存失敗，未知 GUI");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_SUCCESS, "保存成功");

        add(SCAConstants.NARRATION_BRUSH_CANVAS, "畫布");
        add(SCAConstants.NARRATION_BRUSH_TITLE_BOX, "毛筆 GUI");

        add(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_SUCCESS, "保存成功，保存於 {0}");
        add(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_FAIL, "保存失敗，{0}");

        add(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY, "無題");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, "作者：{0}");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY, "佚名");
        add(SCAConstants.TRANSLATE_DRAWING_DATE_KEY, "著墨日期：%1$04d 年 %2$d 月 %3$d 日 %4$02d:%5$02d:%6$02d");

    }
}
