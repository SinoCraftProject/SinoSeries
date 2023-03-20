package games.moegirl.sinocraft.sinocore.tree;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class TreeType {

    public final ResourceLocation name;

    protected TreeType(ResourceLocation name, Map<String, String> languages) {
        this.name = name;
    }


    public class Builder {
        private ResourceLocation name;

        public Builder(ResourceLocation name) {
            this.name = name;
        }

        public Builder language(String locale) {

            return this;
        }
    }
}
