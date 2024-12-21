package games.moegirl.sinocraft.sinobrush.data.gen.advancement;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.stat.SBRStats;
import games.moegirl.sinocraft.sinocore.advancement.criterion.PlayerCustomStatTrigger;
import games.moegirl.sinocraft.sinocore.data.gen.advancement.AbstractAdvancementProvider;
import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.advancement.AdvancementTree;
import games.moegirl.sinocraft.sinocore.data.gen.delegate.AdvancementProviderDelegateBase;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.network.chat.Component;

public class AdvancementProvider extends AbstractAdvancementProvider {
    public AdvancementProvider(IDataGenContext context) {
        super(context);
    }

    @Override
    public void generateData(AdvancementProviderDelegateBase delegate) {
        delegate.addAdvancementTree(saver -> new AdvancementTree(saver)
                .root(modLoc("sinobrush"), Advancement.Builder.advancement()
                        .display(SBRItems.BRUSH.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_ROOT_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_ROOT_DESC),
                                modLoc("advancement/background.png"),
                                AdvancementType.TASK, false, false, false)
                        .addCriterion("join_game", PlayerTrigger.TriggerInstance.tick())
                )
                .push(modLoc("fan"), Advancement.Builder.advancement()
                        .display(SBRItems.FOLDED_FAN.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_FAN_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_FAN_DESC),
                                modLoc("advancement/background.png"),
                                AdvancementType.TASK, true, true, false)
                        .addCriterion("got_fan", delegate.triggerGotItems(SBRItemTags.FAN))
                )
                .child(modLoc("open_fan"), Advancement.Builder.advancement()
                        .display(SBRItems.FAN.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_UNFOLD_FAN_DESC),
                                modLoc("advancement/background.png"),
                                AdvancementType.TASK, true, true, false)
                        .addCriterion("open_fan", PlayerCustomStatTrigger.awardCustomStat(SBRStats.UNFOLD_FAN, 1))
                )
                .pop()
                .child(modLoc("draw_on_paper"), Advancement.Builder.advancement()
                        .display(SBRItems.FILLED_XUAN_PAPER.get(),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_BRUSH_NAME),
                                Component.translatable(SBRConstants.Translation.ADVANCEMENT_BRUSH_DESC),
                                modLoc("advancement/background.png"),
                                AdvancementType.TASK, true, true, false)
                        .addCriterion("draw_on_paper", PlayerCustomStatTrigger.awardCustomStat(SBRStats.DRAW_BY_BRUSH, 1))
                )
        );
    }
}
