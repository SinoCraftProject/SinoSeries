package games.moegirl.sinocraft.sinocore.data.gen.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class AdvancementTree {

    protected Consumer<AdvancementHolder> saver;

    protected AdvancementHolder root;
    protected Deque<AdvancementHolder> cursor = new ArrayDeque<>();

    public AdvancementTree(Consumer<AdvancementHolder> saver, ResourceLocation rootId, Advancement.Builder advancement) {
        this.saver = saver;
        root = advancement.build(rootId);
        saver.accept(root);
        cursor.push(root);
    }

    public AdvancementTree child(ResourceLocation id, Advancement.Builder advancement) {
        advancement.parent(cursor.getFirst());
        var holder = advancement.build(id);
        saver.accept(holder);
        return this;
    }

    public AdvancementTree branch(ResourceLocation id, Advancement.Builder advancement) {
        advancement.parent(cursor.getFirst());
        var holder = advancement.build(id);
        saver.accept(holder);
        cursor.push(holder);
        return this;
    }

    public AdvancementTree endBranch() {
        cursor.pop();
        return this;
    }
}
