package games.moegirl.sinocraft.sinocore.utility;

/**
 * @author luqin2007
 */
public enum RegType {
    BLOCK_MODELS(true, false, true, false, false, true),
    ITEM_MODELS(true, false, true, false, false, true),
    RECIPES(false, false, true, false, true, false),
    BLOCK_TAGS(false, true, true, false, true, false),
    ITEM_TAGS(false, true, true, false, true, false),
    LOOT_TABLES(false, false, true, false, true, false),

    TAB_CONTENTS(false, false, false, true, false, false),
    RENDER_TYPE(false, false, false, true, false, false),
    RENDERER(false, false, false, true, false, false),
    LAYER(false, false, false, true, false, false),

    ALL_MODELS, ALL_TAGS, ALL_PROVIDERS, ALL_EVENTS, ALL_DATA, ALL_RES, ALL;

    public final boolean model, tag, provider, event, data, res;

    RegType() {
        this(false, false, false, false, false, false);
    }

    RegType(boolean model, boolean tag, boolean provider, boolean event, boolean data, boolean res) {
        this.model = model;
        this.tag = tag;
        this.provider = provider;
        this.event = event;
        this.data = data;
        this.res = res;
    }
}
