package games.moegirl.sinocraft.sinocore.datagen.copy.advancement;

import net.minecraft.advancements.Advancement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class AdvancementTree {

    protected Consumer<Advancement> save;

    protected Advancement root;
    protected Deque<Advancement> cursor = new ArrayDeque<>();

    public AdvancementTree(Consumer<Advancement> saveConsumer) {
        this.save = saveConsumer;
    }

    public AdvancementTree root(String id, Advancement.Builder advancement) {
        if (root != null) {
            throw new IllegalStateException("There should only one root.");
        }

        root = advancement.save(save, id);
        cursor.push(root);
        return this;
    }

    public AdvancementTree child(String id, Advancement.Builder advancement) {
        advancement.parent(cursor.getFirst());
        cursor.getFirst().addChild(advancement.save(save, id));
        return this;
    }

    public AdvancementTree push(String id, Advancement.Builder advancement) {
        advancement.parent(cursor.getFirst());
        var adv = advancement.save(save, id);
        cursor.getFirst().addChild(adv);
        cursor.push(adv);
        return this;
    }

    public AdvancementTree pop() {
        cursor.pop();
        return this;
    }
}
