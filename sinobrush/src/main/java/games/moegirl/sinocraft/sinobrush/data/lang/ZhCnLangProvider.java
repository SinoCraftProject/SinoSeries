package games.moegirl.sinocraft.sinobrush.data.lang;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinocore.datagen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.delegate.LanguageProviderDelegateBase;

public class ZhCnLangProvider extends AbstractLanguageProvider {
    public ZhCnLangProvider(IDataGenContext context) {
        super(context, "zh_cn");
    }

    @Override
    public void generateData(LanguageProviderDelegateBase delegate) {
        delegate.addItem(SBRItems.FAN, "折扇（展开）");
        delegate.addItem(SBRItems.FOLDED_FAN, "折扇（收起）");
        delegate.addItem(SBRItems.XUAN_PAPER, "宣纸");
        delegate.addItem(SBRItems.FILLED_XUAN_PAPER, "填充过的宣纸");
        delegate.addItem(SBRItems.INK_BOTTLE, "墨水瓶");
        delegate.addItem(SBRItems.BRUSH, "毛笔");

        delegate.addTab(SBRItems.SINO_BRUSH_TAB, "华夏云墨");

        delegate.add(SBRConstants.Translation.DRAWING_TITLE_LABEL, "画作 ");
        delegate.add(SBRConstants.Translation.DRAWING_TITLE_UNKNOWN, "未命名");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_LABEL, "作者 ");
        delegate.add(SBRConstants.Translation.DRAWING_AUTHOR_UNKNOWN, "佚名");
        delegate.add(SBRConstants.Translation.DRAWING_DATE_LABEL, "着墨日期：$1%s年 $2%s月 $3%s日 $4%s:$5%s:$6%s");

        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN, "展此扇，上有淡墨河山");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FOLDED_FAN, "携此扇，无惧此生风雨");
        delegate.add(SBRConstants.Translation.DESCRIPTION_FAN_WROTE, "一把折扇");
        delegate.add(SBRConstants.Translation.DESCRIPTION_ITEM_COLORED, "已染色 ($1%s,$2%s,$3%s)");
        delegate.add(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, "已扩展 $1%s 次");

        delegate.add(SBRConstants.Translation.HUD_FAN_PREFIX, "对方的折扇上写着：");

        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME, "纸上描眉目，不辨妍或媸");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC, "在纸上着墨");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_NAME, "踏遍山海");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_FAN_DESC, "传说，说书人深湖在 TeaCon 演讲时就用过这把扇子\n他浪迹天地，无长剑在手\n三寸惊堂木，将故事传颂");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME, "侠肝义胆");
        delegate.add(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC, "此展折扇，此任平生不惧风与雪");

    }
}
