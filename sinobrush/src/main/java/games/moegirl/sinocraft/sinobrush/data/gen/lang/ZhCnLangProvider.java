package games.moegirl.sinocraft.sinobrush.data.gen.lang;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;

public class ZhCnLangProvider extends AbstractLanguageProvider {
    public ZhCnLangProvider(IDataGenContext context) {
        super(context, "zh_cn");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addItem(SBRItems.FAN, "展开的折扇");
        delegate.addItem(SBRItems.FOLDED_FAN, "折扇");
        delegate.addItem(SBRItems.XUAN_PAPER, "宣纸");
        delegate.addItem(SBRItems.FILLED_XUAN_PAPER, "填充过的宣纸");
        delegate.addItem(SBRItems.INK_BOTTLE, "墨水瓶");
        delegate.addItem(SBRItems.BRUSH, "毛笔");

        delegate.addCustomStat(SBRStats.DRAW_BY_BRUSH, "作画次数");
        delegate.addCustomStat(SBRStats.UNFOLD_FAN, "展开折扇次数");

        delegate.addTab(SBRItems.SINO_BRUSH_TAB, "华夏云墨");

        delegate.add(SBRConstants.Translation.DRAWING_TITLE_LABEL, "字画 ");
        delegate.add(SBRConstants.Translation.DRAWING_TITLE_UNKNOWN, "未命名");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_LABEL, "作者：");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_UNKNOWN, "佚名");
        delegate.add(SBRConstants.Translation.DRAWING_DATE_LABEL, "着墨日期：%1$s年 %2$s月 %3$s日 %4$s:%5$s:%6$s");

        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN, "展此扇，上有淡墨河山");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN, "携此扇，无惧此生风雨");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_WROTE, "一把折扇");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_WROTE, "一把折扇，上面写着一些字");

        delegate.add(SBRConstants.Translation.DESCRIPTION_ITEM_COLORED, "已染色 (%1$s,%2$s,%3$s)");
        delegate.add(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, "已扩展 %1$s 次");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FILLED_XUAN_PAPER_SIZE, "宽 %1$s，高 %2$s");

        delegate.add(SBRConstants.Translation.HUD_FAN_PREFIX, "对方的折扇上写着：");

        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_SAVE, "保存画布为图片");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_COPY, "左键复制，右键粘贴");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_BRUSH, "落款并署名");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_CLEAR, "清空画布");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_HINT_NAME, "命名字画");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_COPIED, "已将画布复制到剪贴板");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_PASTED, "已从剪贴板读取画布");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_SAVED, "画布图片已保存至 %1$s");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_SUCCESSFUL, "已落款");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_INK, "落款失败：找不到墨水");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_PAPER, "落款失败：找不到宣纸");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_OUTPUT_OCCUPIED, "落款失败：请取走之前的作品");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_BRUSH_ON_HAND, "落款失败：不用笔画画，你是怎么做到的？");

        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_NAME, "丹青描绘浮生，云墨书尽芳华");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_DESC, "欢迎游玩《华夏云墨》");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME, "纸上描眉目，不辨妍或媸");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC, "在纸上着墨");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_NAME, "踏遍山海");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_DESC, "传说，说书人深湖在 TeaCon 演讲时就用过这把扇子\n他浪迹天地，无长剑在手\n三寸惊堂木，将故事传颂");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME, "侠肝义胆");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC, "此展折扇，此任平生不惧风与雪");

        delegate.addItemTag(SBRItemTags.FAN, "折扇");
        delegate.addItemTag(SBRItemTags.XUAN_PAPER, "宣纸");
    }
}
