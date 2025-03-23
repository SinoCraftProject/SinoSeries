package games.moegirl.sinocraft.sinobrush.data.gen.lang;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.LanguageProviderDelegateBase;

public class ZhTwLangProvider extends AbstractLanguageProvider {
    public ZhTwLangProvider(DataGenContext context) {
        super(context, "zh_tw");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addItem(SBRItems.FAN, "展開的摺扇");
        delegate.addItem(SBRItems.FOLDED_FAN, "摺扇");
        delegate.addItem(SBRItems.XUAN_PAPER, "宣紙");
        delegate.addItem(SBRItems.FILLED_XUAN_PAPER, "填充過的宣紙");
        delegate.addItem(SBRItems.INK_BOTTLE, "墨水瓶");
        delegate.addItem(SBRItems.BRUSH, "毛筆");

        delegate.addCustomStat(SBRStats.DRAW_BY_BRUSH, "作畫次數");
        delegate.addCustomStat(SBRStats.UNFOLD_FAN, "展開摺扇次數");

        delegate.addTab(SBRItems.SINO_BRUSH_TAB, "華夏雲墨");

        delegate.add(SBRConstants.Translation.DRAWING_TITLE_LABEL, "字畫 ");
        delegate.add(SBRConstants.Translation.DRAWING_TITLE_UNKNOWN, "未命名");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_LABEL, "作者：");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_UNKNOWN, "佚名");
        delegate.add(SBRConstants.Translation.DRAWING_DATE_LABEL, "著墨日期：%1$s年 %2$s月 %3$s日 %4$s:%5$s:%6$s");

        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_1, "展此扇，上有淡墨河山");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN_2, "攜此扇，無懼此生風雨");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_EMPTY, "一把摺扇");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_WROTE, "一把摺扇，上面寫著一些字");

        delegate.add(SBRConstants.Translation.DESCRIPTION_ITEM_COLORED, "已染色 (%1$s,%2$s,%3$s)");
        delegate.add(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, "已擴展 %1$s 次");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FILLED_XUAN_PAPER_SIZE, "寬 %1$s，高 %2$s");

        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_SAVE, "保存畫布為圖片");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_COPY, "左鍵複製，右鍵黏貼");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_BRUSH, "落款並署名");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_TOOLTIP_CLEAR, "清空畫布");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_HINT_NAME, "命名字畫");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_COPIED, "已將畫布複製到剪貼簿");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_PASTED, "已從剪貼簿讀取畫布");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_CANVAS_SAVED, "畫布圖片已保存至 %1$s");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_SUCCESSFUL, "已落款");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_INK, "落款失敗：找不到墨水");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_PAPER, "落款失敗：找不到宣紙");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_OUTPUT_OCCUPIED, "落款失敗：請取走之前的作品");
        delegate.add(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_BRUSH_ON_HAND, "落款失敗：不用筆畫畫，你是怎麼做到的？");
        delegate.add(SBRConstants.Translation.GUI_FAN_SETTING_HUD_POSITION, "設定 HUD 位置");
        delegate.add(SBRConstants.Translation.GUI_FAN_SETTING_HUD_HINT, "拖動調整位置，滾動滾輪調整大小");

        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_NAME, "丹青描繪浮生，雲墨書盡芳華");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_ROOT_DESC, "歡迎遊玩《華夏雲墨》");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME, "紙上描眉目，不辨妍或媸");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC, "在紙上著墨");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_NAME, "踏遍山海");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_DESC, "傳說，說書人深湖在 TeaCon 演講時就用過這把扇子\n他浪跡天地，無長劍在手\n三寸驚堂木，將故事傳頌");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME, "俠肝義膽");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC, "此展摺扇，此任平生不懼風與雪");

        delegate.addItemTag(SBRItemTags.FAN, "摺扇");
        delegate.addItemTag(SBRItemTags.XUAN_PAPER, "宣紙");
    }
}
