package games.moegirl.sinocraft.sinocalligraphy.data.advancement;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class SCAAdvancementGeneratorBrush implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider lookupProvider, Consumer<Advancement> consumer,
                         ExistingFileHelper existingFileHelper) {
        var root = Advancement.Builder.advancement()
                .display(SCAItems.BRUSH.get(),
                        Component.translatable("sinocalligraphy.advancements.sca"),
                        Component.translatable("sinocalligraphy.advancements.sca.desc"),
                        new ResourceLocation(SinoCalligraphy.MODID, "textures/advancement/white_marble.png"),
                        FrameType.TASK, false, true, false)
                .addCriterion("got_brush", InventoryChangeTrigger.TriggerInstance.hasItems(SCAItems.BRUSH.get()))
                .rewards(AdvancementRewards.EMPTY)
                .save(consumer, "sinocalligraphy:root");

//        Advancement.Builder.advancement()
//                .display(SCAItems.INK.get(),
//                        Component.translatable("sinocalligraphy.advancements.ink"),
//                        Component.translatable("sinocalligraphy.advancements.ink.desc"),
//                        new ResourceLocation(SinoCalligraphy.MODID, "textures/advancement/white_marble.png"),
//                        FrameType.TASK, false, true, false)
//                .addCriterion("ink", new InventoryChangeTrigger.TriggerInstance(
//                        EntityPredicate.Composite.ANY,
//                        MinMaxBounds.Ints.ANY,
//                        MinMaxBounds.Ints.ANY,
//                        MinMaxBounds.Ints.ANY,
//                        new ItemPredicate[]{ItemPredicate.Builder.item().of(SCAItems.INK.get()).build()}))
//                .parent(root)
//                .rewards(AdvancementRewards.EMPTY)
//                .save(consumer, "sinocalligraphy:ink");
//
//        Advancement.Builder.advancement()
//                .display(SCAItems.FILLED_XUAN_PAPER.get(),
//                        Component.translatable("sinocalligraphy.advancements.draw"),
//                        Component.translatable("sinocalligraphy.advancements.draw.desc"),
//                        new ResourceLocation(SinoCalligraphy.MODID, "textures/advancement/white_marble.png"),
//                        FrameType.TASK, false, true, false)
//                .addCriterion("draw", new InventoryChangeTrigger.TriggerInstance(
//                        EntityPredicate.Composite.ANY,
//                        MinMaxBounds.Ints.ANY,
//                        MinMaxBounds.Ints.ANY,
//                        MinMaxBounds.Ints.ANY,
//                        new ItemPredicate[]{ItemPredicate.Builder.item().of(SCAItems.FILLED_XUAN_PAPER.get()).build()}))
//                .parent(root)
//                .rewards(AdvancementRewards.EMPTY)
//                .save(consumer, "sinocalligraphy:draw");

        Advancement.Builder.advancement()
                .display(SCAItems.FAN.get(),
                        Component.translatable("sinocalligraphy.advancements.fan"),
                        Component.translatable("sinocalligraphy.advancements.fan.desc"),
                        new ResourceLocation(SinoCalligraphy.MODID, "textures/advancement/white_marble.png"),
                        FrameType.TASK, false, true, true)
                .addCriterion("fan", new InventoryChangeTrigger.TriggerInstance(
                        EntityPredicate.Composite.ANY,
                        MinMaxBounds.Ints.ANY,
                        MinMaxBounds.Ints.ANY,
                        MinMaxBounds.Ints.ANY,
                        new ItemPredicate[]{ItemPredicate.Builder.item().of(SCAItems.FAN.get()).build()}))
                .parent(root)
                .rewards(AdvancementRewards.EMPTY)
                .save(consumer, "sinocalligraphy:fan");
    }
}
