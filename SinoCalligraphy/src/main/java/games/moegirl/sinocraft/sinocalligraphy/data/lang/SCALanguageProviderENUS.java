package games.moegirl.sinocraft.sinocalligraphy.data.lang;

import games.moegirl.sinocraft.sinocalligraphy.item.FanItem;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.LanguageProviderBase;
import net.minecraft.data.PackOutput;

public class SCALanguageProviderENUS extends LanguageProviderBase {
    public SCALanguageProviderENUS(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    public void translate() {
        addItem(SCAItems.BRUSH, "Chinese brush");
        addItem(SCAItems.FAN, "Unfolded fan");
        addItem(SCAItems.FAN_FOLDED, "Fan");

        add(FanItem.FOLDED_DESCRIPTION_LINE_1, "Legend said, TaoismDeeplake used this fan during his speech.");
        add(FanItem.FOLDED_DESCRIPTION_LINE_2, "He wandered the world without a sword in his hand.");
        add(FanItem.UNFOLDED_DESCRIPTION_LINE_1, "Opening this fan, Rivers and mountains rendered by light ink floats on it.");
        add(FanItem.UNFOLDED_DESCRIPTION_LINE_2, "Carrying this fan, there will be no fear of disappointing life. fears.");

        add("sinocalligraphy.advancements.sca", "SinoCalligraphy");
        add("sinocalligraphy.advancements.sca.desc", "The painting that used color to depict all walks of life, while retained black and white to render the beauty of ages. ");
        add("sinocalligraphy.advancements.draw", "On a paper, eyes can be brought to life, whether they are beautiful, or ugly.");
        add("sinocalligraphy.advancements.draw.desc", "Once put a drop of ink on paper.");
        add("sinocalligraphy.advancements.ink", "One will see it stained, suddenly, yet silently.");
        add("sinocalligraphy.advancements.ink.desc", "That is it, no matter how to get some ink.");
        add("sinocalligraphy.advancements.fan", "Once holding this fan, one will be not afraid of torture and misery.");
        add("sinocalligraphy.advancements.fan.desc", "Once holding this fan, the spirit of a knightly and free man, is self-evident.");
    }
}
