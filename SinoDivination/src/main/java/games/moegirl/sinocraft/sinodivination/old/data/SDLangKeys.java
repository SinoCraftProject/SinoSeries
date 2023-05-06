package games.moegirl.sinocraft.sinodivination.old.data;

import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SDBlockEntities;
import games.moegirl.sinocraft.sinodivination.old.recipe.SDRecipes;

import java.util.Set;
import java.util.TreeSet;

public class SDLangKeys {

    public static final Set<String> TRANSLATION_KEYS = new TreeSet<>();

    public static final String SYMBOL_NAME = buildTranslationKey("symbol", "name");
    public static final String SYMBOL_DATE = buildTranslationKey("symbol", "date");

    public static final String SILKWORM_PLAGUE_TITLE = buildTranslationKey("gui", SDBlockEntities.SILKWORM_PLAQUE.getId().getPath(), "title");
    public static final String CARVING_TABLE_TITLE = buildTranslationKey("gui", SDBlocks.CARVING_TABLE.getId().getPath(), "title");

    public static final String JEI_RECIPE_CHANGE_SOUP = buildTranslationKey("jei", SDRecipes.CHANGE_SOUP.name().getPath(), "recipe");
    public static final String JEI_RECIPE_CARVING_TABLE = buildTranslationKey("jei", SDRecipes.CARVING_TABLE.name().getPath(), "recipe");
    public static final String JEI_RECIPE_KETTLE_POT = buildTranslationKey("jei", SDRecipes.KETTLE_POT.name().getPath(), "recipe");

    public static final String TOP_BLOCK_OWNER = buildTranslationKey("top", "block", "owner");
    public static final String TOP_BLOCK_ALLOWED = buildTranslationKey("top", "block", "allowed");
    public static final String TOP_BIRTHDAY = buildTranslationKey("top", "birthday");
    public static final String TOP_BIRTHDAY_NO = buildTranslationKey("top", "birthday", "no");

    public static String buildTranslationKey(String type, String key) {
        String k = SinoDivination.MODID + "." + type + "." + key;
        TRANSLATION_KEYS.add(k);
        return k;
    }

    public static String buildTranslationKey(String type, String... params) {
        return buildTranslationKey(type, String.join("_", params));
    }
}
