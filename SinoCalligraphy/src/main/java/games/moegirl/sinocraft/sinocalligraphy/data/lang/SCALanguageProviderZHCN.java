package games.moegirl.sinocraft.sinocalligraphy.data.lang;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
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
        addItem(SCAItems.EMPTY_XUAN_PAPER_RED, "红色的宣纸");
        addItem(SCAItems.EMPTY_XUAN_PAPER_BLACK, "黑色的宣纸");
        addItem(SCAItems.FILLED_XUAN_PAPER, "填充过的宣纸");
        addItem(SCAItems.INK, "墨汁");
        addItem(SCAItems.GOLDEN_INK, "加金粉的墨汁");

        add(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY, "无题字画");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, "作者：");
        add(SCAConstants.TRANSLATE_DRAWING_AUTHOR_UNKNOWN_KEY, "佚名");

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
    }
}
