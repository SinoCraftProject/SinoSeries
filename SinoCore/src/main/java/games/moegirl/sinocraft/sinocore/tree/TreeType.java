package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TreeType {

    public final ResourceLocation name;

    protected Map<String, String> translates;

    protected TreeType(ResourceLocation name, Map<String, String> translates) {
        this.name = name;
        this.translates = translates;
    }


    public class Builder {
        protected ResourceLocation name;

        protected Map<String, String> translates = new HashMap<>();

        public Builder(ResourceLocation name) {
            this.name = name;
        }

        // Todo: qyl27: custom translates for a specific block/item.
        public Builder translate(String locale, String translate) {
            translates.put(locale, translate);
            return this;
        }
    }
}
