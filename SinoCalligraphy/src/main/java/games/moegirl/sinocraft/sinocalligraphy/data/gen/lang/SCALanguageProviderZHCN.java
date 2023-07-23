package games.moegirl.sinocraft.sinocalligraphy.data.gen.lang;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.block.SCABlocks;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class SCALanguageProviderZHCN extends AbstractLanguageProvider {
    public SCALanguageProviderZHCN(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addItem(SCAItems.BRUSH, "毛笔");
        addItem(SCAItems.FAN, "展开的折扇");
        addItem(SCAItems.FAN_FOLDED, "折扇");
        addItem(SCAItems.EMPTY_XUAN_PAPER, "宣纸");
        addItem(SCAItems.FILLED_XUAN_PAPER, "填充过的宣纸");
        addItem(SCAItems.INK, "墨汁");
        addItem(SCAItems.GOLDEN_INK, "加金粉的墨汁");
        addItem(SCAItems.GREEN_SANDALWOOD_BARK, "青檀树皮");
        addItem(SCAItems.WOOD_PULP_BUCKET, "木浆桶");

        addBlock(SCABlocks.PAPER_DRYING_RACK, "晾纸架");
        addBlock(SCABlocks.WOOD_PULP_BLOCK, "木浆");

        add(SCAConstants.DESCRIPTION_ID_WOOD_PULP, "木浆");

        add(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_1, "传说，道家深湖在演讲时就用过这把扇子");
        add(SCAConstants.TRANSLATE_FOLDED_DESCRIPTION_LINE_2, "他浪迹天地，无长剑在手");
        add(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_1, "开此扇，上有淡墨河山");
        add(SCAConstants.TRANSLATE_UNFOLDED_DESCRIPTION_LINE_2, "携此扇，无惧此生风雨");

        add(SCAConstants.TRANSLATE_ADVANCEMENT_SCA, "华夏丹青");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_SCA_DESC, "丹青描绘浮生，云墨书尽芳华");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING, "纸上描眉目，不辨妍或媸");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING_DESC, "在纸上着墨");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_INK, "墨染，蓦然，默然");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_INK_DESC, "获得一些墨汁");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_FAN, "此展折扇，此任平生不惧风与雪");
        add(SCAConstants.TRANSLATE_ADVANCEMENT_FAN_DESC, "此身洒脱，此间侠义何需他人说");

        add(SCAConstants.GUI_BRUSH_COPY_BUTTON_TOOLTIP, "左键复制到剪贴板，右键粘贴");
        add(SCAConstants.GUI_BRUSH_OUTPUT_BUTTON_TOOLTIP, "保存到文件");
        add(SCAConstants.GUI_BRUSH_APPLY_BUTTON_TOOLTIP, "保存");
        add(SCAConstants.GUI_BRUSH_CLEAR_BUTTON_TOOLTIP, "清空画布");

        add(SCAConstants.GUI_MESSAGE_BRUSH_COPIED, "复制成功");
        add(SCAConstants.GUI_MESSAGE_BRUSH_PASTED, "粘贴成功");
        add(SCAConstants.GUI_MESSAGE_BRUSH_PASTE_FAILED, "粘贴失败");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_INK, "保存失败，请放入墨汁");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_PAPER, "保存失败，请放入宣纸");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_FAILED_UNKNOWN_SCREEN, "保存失败，未知 GUI");
        add(SCAConstants.GUI_MESSAGE_BRUSH_SAVE_SUCCESS, "保存成功");

        add(SCAConstants.NARRATION_BRUSH_CANVAS, "画布");
        add(SCAConstants.NARRATION_BRUSH_TITLE_BOX, "毛笔 GUI");

        add(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_SUCCESS, "保存成功，保存于 {0}");
        add(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_FAIL, "保存失败，{0}");

        add(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY, "无题");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, "作者：{0}");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY, "佚名");
        add(SCAConstants.TRANSLATE_DRAWING_DATE_KEY, "着墨日期：%1$04d 年 %2$d 月 %3$d 日 %4$02d:%5$02d:%6$02d");

    }
}
