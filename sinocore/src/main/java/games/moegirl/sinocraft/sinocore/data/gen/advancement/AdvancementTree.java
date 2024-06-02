package games.moegirl.sinocraft.sinocore.data.gen.advancement;

import net.minecraft.advancements.Advancement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class AdvancementTree {

    protected Consumer<Advancement> saver;

    protected Advancement root;
    protected Deque<Advancement> cursor = new ArrayDeque<>();

    public AdvancementTree(Consumer<Advancement> saver) {
        this.saver = saver;
    }

    public AdvancementTree root(String id, Advancement.Builder advancement) {
        if (root != null) {
            throw new IllegalStateException("There should only one root.");
        }

        root = advancement.save(saver, id);
        cursor.push(root);
        return this;
    }

    public AdvancementTree child(String id, Advancement.Builder advancement) {
        advancement.parent(cursor.getFirst());
        advancement.save(saver, id);
        return this;
    }

    public AdvancementTree push(String id, Advancement.Builder advancement) {
        advancement.parent(cursor.getFirst());
        var adv = advancement.save(saver, id);
        cursor.push(adv);
        return this;
    }

    public AdvancementTree pop() {
        cursor.pop();
        return this;
    }
}
