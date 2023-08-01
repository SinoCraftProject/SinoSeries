package games.moegirl.sinocraft.sinofeast.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinofeast.SFConstants;
import net.minecraft.data.PackOutput;

public class SFLanguageProviderZHCN extends AbstractLanguageProvider {
    public SFLanguageProviderZHCN(PackOutput output, String modid) {
        super(output, modid, "zh_cn");
    }

    @Override
    public void translate() {
        add(SFConstants.TRANSLATE_TASTE_EMPTY, "无");
        add(SFConstants.TRANSLATE_TASTE_SOUR, "酸");
        add(SFConstants.TRANSLATE_TASTE_SWEET, "甜");
        add(SFConstants.TRANSLATE_TASTE_BITTER, "苦");
        add(SFConstants.TRANSLATE_TASTE_SPICY, "辣");
        add(SFConstants.TRANSLATE_TASTE_SALTY, "咸");
        add(SFConstants.TRANSLATE_TASTE_PUNGENT, "麻");
        add(SFConstants.TRANSLATE_TASTE_ACRID, "辛");
        add(SFConstants.TRANSLATE_TASTE_FRESH, "鲜");

        add(SFConstants.TRANSLATE_PREFER_TOOLTIP, "你特别喜欢这个食物。");
        add(SFConstants.TRANSLATE_LIKE_TOOLTIP, "你喜欢这个食物。");
        add(SFConstants.TRANSLATE_DISLIKE_TOOLTIP, "你不喜欢这个食物。");
    }
}
