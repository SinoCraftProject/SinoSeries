package games.moegirl.sinocraft.sinocalligraphy.data.lang;

import games.moegirl.sinocraft.sinocalligraphy.item.FanItem;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.LanguageProviderBase;
import net.minecraft.data.PackOutput;

public class SCALanguageProviderZHCN extends LanguageProviderBase {
    public SCALanguageProviderZHCN(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addItem(SCAItems.BRUSH, "毛笔");
        addItem(SCAItems.FAN, "展开的折扇");
        addItem(SCAItems.FAN_FOLDED, "折扇");

        add(FanItem.FOLDED_DESCRIPTION_LINE_1, "传说，道家深湖在演讲时就用过这把扇子");
        add(FanItem.FOLDED_DESCRIPTION_LINE_2, "他浪迹天地，无长剑在手");
        add(FanItem.UNFOLDED_DESCRIPTION_LINE_1, "开此扇，上有淡墨河山");
        add(FanItem.UNFOLDED_DESCRIPTION_LINE_2, "携此扇，无惧此生风雨");

        add("sinocalligraphy.advancements.sca", "华夏丹青");
        add("sinocalligraphy.advancements.sca.desc", "丹青描绘浮生，云墨书尽芳华");
        add("sinocalligraphy.advancements.draw", "纸上描眉目，不辨妍或媸");
        add("sinocalligraphy.advancements.draw.desc", "在纸上着墨");
        add("sinocalligraphy.advancements.ink", "墨染，蓦然，默然");
        add("sinocalligraphy.advancements.ink.desc", "不论用什么方法获得一些墨汁");
        add("sinocalligraphy.advancements.fan", "此展折扇，此任平生不惧风与雪");
        add("sinocalligraphy.advancements.fan.desc", "此身洒脱，此间侠义何需他人说");
    }
}
