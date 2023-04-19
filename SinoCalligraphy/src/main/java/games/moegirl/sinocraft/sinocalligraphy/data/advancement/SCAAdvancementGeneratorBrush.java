package games.moegirl.sinocraft.sinocalligraphy.data.advancement;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.data.SCAItemTags;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractAdvancementGenerator;
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

public class SCAAdvancementGeneratorBrush extends AbstractAdvancementGenerator {
    public SCAAdvancementGeneratorBrush(String modid) {
        super(modid, new ResourceLocation(SinoCalligraphy.MODID, "textures/advancement/white_marble.png"));
    }

    @Override
    public void addAdvancements(Consumer<Advancement> consumer) {
        var root = Advancement.Builder.advancement()
                .display(display(SCAItems.BRUSH.get(),
                        SCAConstants.TRANSLATE_ADVANCEMENT_SCA, SCAConstants.TRANSLATE_ADVANCEMENT_SCA_DESC,
                        FrameType.TASK))
                .addCriterion("got_brush", triggerGotItems(SCAItemTags.BRUSHES))
                .rewards(AdvancementRewards.EMPTY)
                .save(consumer, moddedId("root"));

        Advancement.Builder.advancement()
                .display(display(SCAItems.INK.get(),
                        SCAConstants.TRANSLATE_ADVANCEMENT_INK, SCAConstants.TRANSLATE_ADVANCEMENT_INK_DESC,
                        FrameType.TASK))
                .addCriterion("got_ink", triggerGotItems(SCAItemTags.INKS))
                .parent(root)
                .rewards(AdvancementRewards.EMPTY)
                .save(consumer, moddedId("ink"));

        Advancement.Builder.advancement()
                .display(display(SCAItems.FILLED_XUAN_PAPER.get(),
                        SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING, SCAConstants.TRANSLATE_ADVANCEMENT_DRAWING_DESC,
                        FrameType.TASK))
                .addCriterion("got_drawing", triggerGotItems(SCAItemTags.FILLED_PAPERS))
                .parent(root)
                .rewards(AdvancementRewards.EMPTY)
                .save(consumer, moddedId("drawing"));

        Advancement.Builder.advancement()
                .display(display(SCAItems.FAN.get(),
                        SCAConstants.TRANSLATE_ADVANCEMENT_FAN, SCAConstants.TRANSLATE_ADVANCEMENT_FAN_DESC,
                        FrameType.TASK))
                .addCriterion("got_fan", triggerGotItems(SCAItemTags.FAN))
                .parent(root)
                .rewards(AdvancementRewards.EMPTY)
                .save(consumer, moddedId("fan"));
    }
}
