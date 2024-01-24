package games.moegirl.sinocraft.sinocore.datagen.copy;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    protected final String modid;
    protected final ResourceLocation background;

    public AbstractAdvancementGenerator(String modid, ResourceLocation background) {
        this.modid = modid;
        this.background = background;
    }

    public abstract void addAdvancements(Consumer<AdvancementHolder> consumer);

    @Override
    public void generate(HolderLookup.Provider arg, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
        addAdvancements(consumer);
    }

    public CriterionTriggerInstance triggerGotItems(ItemLike... item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item).triggerInstance();
    }

    public CriterionTriggerInstance triggerGotItems(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate(Optional.of(tag),
                Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, List.of(),
                List.of(), Optional.empty(), Optional.empty())).triggerInstance();
    }

    public String moddedId(String id) {
        return modid + ":" + id;
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType) {
        return display(item, name, desc, frameType, false);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType,
                               boolean showToast) {
        return display(item, name, desc, frameType, showToast, true);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType,
                               boolean showToast, boolean announceToChat) {
        return display(item, name, desc, frameType, showToast, announceToChat, false);
    }

    public DisplayInfo display(Item item, String name, String desc, FrameType frameType,
                               boolean showToast, boolean announceToChat, boolean hidden) {
        return new DisplayInfo(new ItemStack(item), Component.translatable(name), Component.translatable(desc),
                background, frameType, showToast, announceToChat, hidden);
    }
}
