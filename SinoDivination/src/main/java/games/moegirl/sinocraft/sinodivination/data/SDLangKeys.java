package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;

import java.util.Set;
import java.util.TreeSet;

public class SDLangKeys {

    public static final Set<String> TRANSLATION_KEYS = new TreeSet<>();

    public static final String SYMBOL_NAME = buildTranslationKey("symbol", "name");
    public static final String SYMBOL_DATE = buildTranslationKey("symbol", "date");
//
    public static final String SILKWORM_PLAGUE_TITLE = buildTranslationKey("gui", SDBlockEntities.SILKWORM_PLAQUE.getId().getPath(), "title");
    public static final String CARVING_TABLE_TITLE = buildTranslationKey("gui", SDBlocks.CARVING_TABLE.getId().getPath(), "title");
//
    public static final String JEI_RECIPE_CHANGE_SOUP = buildTranslationKey("jei", SDRecipes.CHANGE_SOUP.name().getPath(), "recipe");
    public static final String JEI_RECIPE_CARVING_TABLE = buildTranslationKey("jei", SDRecipes.CARVING_TABLE.name().getPath(), "recipe");
    public static final String JEI_RECIPE_KETTLE_POT = buildTranslationKey("jei", SDRecipes.KETTLE_POT.name().getPath(), "recipe");
//
    public static final String TOP_BLOCK_OWNER = buildTranslationKey("top", "block", "owner");
    public static final String TOP_BLOCK_ALLOWED = buildTranslationKey("top", "block", "allowed");
    public static final String TOP_BIRTHDAY = buildTranslationKey("top", "birthday");
    public static final String TOP_BIRTHDAY_NO = buildTranslationKey("top", "birthday", "no");

    public static final String DIARY0000 = buildTranslationKey("adventurer_diary", "00", "00");
    public static final String DIARY0001 = buildTranslationKey("adventurer_diary", "00", "01");
    public static final String DIARY0002 = buildTranslationKey("adventurer_diary", "00", "02");
    public static final String DIARY0100 = buildTranslationKey("adventurer_diary", "01", "00");
    public static final String DIARY0101 = buildTranslationKey("adventurer_diary", "01", "01");
    public static final String DIARY0200 = buildTranslationKey("adventurer_diary", "02", "00");
    public static final String DIARY0201 = buildTranslationKey("adventurer_diary", "02", "01");
    public static final String DIARY0202 = buildTranslationKey("adventurer_diary", "02", "02");
    public static final String DIARY0300 = buildTranslationKey("adventurer_diary", "03", "00");
    public static final String DIARY0301 = buildTranslationKey("adventurer_diary", "03", "01");
    public static final String DIARY0302 = buildTranslationKey("adventurer_diary", "03", "02");
    public static final String DIARY0303 = buildTranslationKey("adventurer_diary", "03", "03");
    public static final String DIARY0304 = buildTranslationKey("adventurer_diary", "03", "04");
    public static final String DIARY0305 = buildTranslationKey("adventurer_diary", "03", "05");
    public static final String DIARY0306 = buildTranslationKey("adventurer_diary", "03", "06");
    public static final String DIARY0307 = buildTranslationKey("adventurer_diary", "03", "07");
    public static final String DIARY0400 = buildTranslationKey("adventurer_diary", "04", "00");
    public static final String DIARY0401 = buildTranslationKey("adventurer_diary", "04", "01");
    public static final String DIARY0402 = buildTranslationKey("adventurer_diary", "04", "02");
    public static final String DIARY0403 = buildTranslationKey("adventurer_diary", "04", "03");
    public static final String DIARY0404 = buildTranslationKey("adventurer_diary", "04", "04");
    public static final String DIARY0405 = buildTranslationKey("adventurer_diary", "04", "05");
    public static final String DIARY0500 = buildTranslationKey("adventurer_diary", "05", "00");
    public static final String DIARY0501 = buildTranslationKey("adventurer_diary", "05", "01");
    public static final String DIARY0502 = buildTranslationKey("adventurer_diary", "05", "02");
    public static final String DIARY0600 = buildTranslationKey("adventurer_diary", "06", "00");
    public static final String DIARY0601 = buildTranslationKey("adventurer_diary", "06", "01");
    public static final String DIARY0700 = buildTranslationKey("adventurer_diary", "07", "00");
    public static final String DIARY0701 = buildTranslationKey("adventurer_diary", "07", "01");
    public static final String DIARY0800 = buildTranslationKey("adventurer_diary", "08", "00");
    public static final String DIARY0801 = buildTranslationKey("adventurer_diary", "08", "01");
    public static final String DIARY0900 = buildTranslationKey("adventurer_diary", "09", "00");
    public static final String DIARY0901 = buildTranslationKey("adventurer_diary", "09", "01");
    public static final String DIARY0902 = buildTranslationKey("adventurer_diary", "09", "02");
    public static final String DIARY1000 = buildTranslationKey("adventurer_diary", "10", "00");
    public static final String DIARY1001 = buildTranslationKey("adventurer_diary", "10", "01");
    public static final String DIARY1002 = buildTranslationKey("adventurer_diary", "10", "02");
    public static final String DIARY1100 = buildTranslationKey("adventurer_diary", "11", "00");
    public static final String DIARY1101 = buildTranslationKey("adventurer_diary", "11", "01");
    public static final String DIARY1102 = buildTranslationKey("adventurer_diary", "11", "02");
    public static final String DIARY1103 = buildTranslationKey("adventurer_diary", "11", "03");
    public static final String DIARY1104 = buildTranslationKey("adventurer_diary", "11", "04");
    public static final String DIARY1105 = buildTranslationKey("adventurer_diary", "11", "05");
    public static final String DIARY1106 = buildTranslationKey("adventurer_diary", "11", "06");
    public static final String DIARY1107 = buildTranslationKey("adventurer_diary", "11", "07");
    public static final String DIARY1108 = buildTranslationKey("adventurer_diary", "11", "08");
    public static final String DIARY1109 = buildTranslationKey("adventurer_diary", "11", "09");
    public static final String DIARY1200 = buildTranslationKey("adventurer_diary", "12", "00");
    public static final String DIARY1201 = buildTranslationKey("adventurer_diary", "12", "01");
    public static final String DIARY1202 = buildTranslationKey("adventurer_diary", "12", "02");
    public static final String DIARY1203 = buildTranslationKey("adventurer_diary", "12", "03");
    public static final String DIARY1204 = buildTranslationKey("adventurer_diary", "12", "04");
    public static final String DIARY1205 = buildTranslationKey("adventurer_diary", "12", "05");
    public static final String DIARY1206 = buildTranslationKey("adventurer_diary", "12", "06");

    public static String buildTranslationKey(String type, String key) {
        String k = SinoDivination.MODID + "." + type + "." + key;
        TRANSLATION_KEYS.add(k);
        return k;
    }

    public static String buildTranslationKey(String type, String... params) {
        return buildTranslationKey(type, String.join("_", params));
    }
}
