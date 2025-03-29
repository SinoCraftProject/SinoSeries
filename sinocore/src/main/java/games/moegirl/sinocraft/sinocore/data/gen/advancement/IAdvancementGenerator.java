package games.moegirl.sinocraft.sinocore.data.gen.advancement;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;

import java.util.function.Consumer;

public interface IAdvancementGenerator {

    void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, DataGenContext context);

    /**
     * 若返回 null，使用 Forge 默认实现
     */
    default AdvancementSubProvider toSubProvider(DataGenContext context) {
        return null;
    }
}
