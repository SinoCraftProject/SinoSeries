package games.moegirl.sinocraft.sinodivination.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * 冒险者的日记
 * <p>
 * <a href="https://minecraft.fandom.com/zh/wiki/%E5%8E%9F%E5%A7%8BJSON%E6%96%87%E6%9C%AC%E6%A0%BC%E5%BC%8F">原始JSON文本格式</a>
 * </p>
 *
 * @author luqin2007
 */
public class VictimsDiary {

    public static ItemStack build() {
        ItemStack diary = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = diary.getOrCreateTag();
        tag.putString("author", "qyl");
        tag.putString("pages", buildPages().toString());
        tag.putString("title", "遇难者的日记");
        return diary;
    }

    private static JsonArray buildPages() {
        JsonArray pages = new JsonArray();
        pages.add(buildPage(SDLangKeys.DIARY0000, SDLangKeys.DIARY0001, SDLangKeys.DIARY0002));
        pages.add(buildPage(SDLangKeys.DIARY0100, SDLangKeys.DIARY0101));
        pages.add(buildPage(SDLangKeys.DIARY0200, SDLangKeys.DIARY0201, SDLangKeys.DIARY0202));
        pages.add(buildPage(SDLangKeys.DIARY0300, SDLangKeys.DIARY0301, SDLangKeys.DIARY0302, SDLangKeys.DIARY0303,
                SDLangKeys.DIARY0304, SDLangKeys.DIARY0305, SDLangKeys.DIARY0306, SDLangKeys.DIARY0307));
        pages.add(buildPage(SDLangKeys.DIARY0400, SDLangKeys.DIARY0401, SDLangKeys.DIARY0402, SDLangKeys.DIARY0403,
                SDLangKeys.DIARY0404, SDLangKeys.DIARY0405));
        pages.add(buildPage(SDLangKeys.DIARY0500, SDLangKeys.DIARY0501, SDLangKeys.DIARY0502));
        pages.add(buildPage(SDLangKeys.DIARY0600, SDLangKeys.DIARY0601));
        pages.add(buildPage(SDLangKeys.DIARY0700, SDLangKeys.DIARY0701));
        pages.add(buildPage(SDLangKeys.DIARY0800, SDLangKeys.DIARY0801));
        pages.add(buildPage(SDLangKeys.DIARY0900, SDLangKeys.DIARY0901, SDLangKeys.DIARY0902));
        pages.add(buildPage(SDLangKeys.DIARY1000, SDLangKeys.DIARY1001, SDLangKeys.DIARY1002));
        pages.add(buildPage(SDLangKeys.DIARY1100, SDLangKeys.DIARY1101, SDLangKeys.DIARY1102, SDLangKeys.DIARY1103,
                SDLangKeys.DIARY1104, SDLangKeys.DIARY1105, SDLangKeys.DIARY1106, SDLangKeys.DIARY1107,
                SDLangKeys.DIARY1108, SDLangKeys.DIARY1109));
        pages.add(buildPage(SDLangKeys.DIARY1200, SDLangKeys.DIARY1201, SDLangKeys.DIARY1202, SDLangKeys.DIARY1203,
                SDLangKeys.DIARY1204, SDLangKeys.DIARY1205, SDLangKeys.DIARY1206));
        return pages;
    }

    private static JsonObject buildPage(String titleKey, String... contentKeys) {
        JsonObject page = new JsonObject();
        JsonArray paragraph = new JsonArray();
        page.addProperty("translate", titleKey);
        page.addProperty("color", "red");
        page.addProperty("bold", true);
        page.add("extra", paragraph);

        for (String contentKey : contentKeys) {
            JsonObject p = new JsonObject();
            p.addProperty("translate", contentKey);
            p.addProperty("color", "reset");
            p.addProperty("bold", false);
            paragraph.add(p);
        }
        return page;
    }
}
