package games.moegirl.sinocraft.sinobrush.data.advancement;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.data.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.advancement.criterion.CustomStatTrigger;
import games.moegirl.sinocraft.sinocore.datagen.AbstractAdvancementProvider;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.datagen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.datagen.delegate.AdvancementProviderDelegateBase;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AdvancementProvider extends AbstractAdvancementProvider {
    public AdvancementProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(AdvancementProviderDelegateBase delegate) {
        delegate.addAdvancementTree(saver -> new AdvancementTree(saver)
                .root("sinobrush", Advancement.Builder.advancement()
                        .display(SBRItems.BRUSH.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_ROOT_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_ROOT_DESC),
                                new ResourceLocation(SinoBrush.MODID, "advancement/background"),
                                FrameType.TASK, false, false, false)
                        .addCriterion("join_game", PlayerTrigger.TriggerInstance.tick())
                )
                .push("fan", Advancement.Builder.advancement()
                        .display(SBRItems.FOLDED_FAN.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_FAN_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_FAN_DESC),
                                new ResourceLocation(SinoBrush.MODID, "advancement/background"),
                                FrameType.TASK, true, true, false)
                        .addCriterion("got_fan", delegate.triggerGotItems(SBRItemTags.FAN))
                )
                .child("open_fan", Advancement.Builder.advancement()
                        .display(SBRItems.FAN.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC),
                                new ResourceLocation(SinoBrush.MODID, "advancement/background"),
                                FrameType.TASK, true, true, false)
                        .addCriterion("open_fan", CustomStatTrigger.Instance.create(SBRStats.UNFOLD_FAN.get(), 1))
                )
                .pop()
                .child("draw_on_paper", Advancement.Builder.advancement()
                        .display(SBRItems.FILLED_XUAN_PAPER.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC),
                                new ResourceLocation(SinoBrush.MODID, "advancement/background"),
                                FrameType.TASK, true, true, false)
                        .addCriterion("draw_on_paper", CustomStatTrigger.Instance.create(SBRStats.DRAW_BY_BRUSH.get(), 1))
                )
        );
    }
}
