package games.moegirl.sinocraft.sinocalligraphy.item;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.client.XuanPaperRenderer;
import games.moegirl.sinocraft.sinocalligraphy.drawing.DrawingDataVersion;
import games.moegirl.sinocraft.sinocore.item.tab.ITabItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class FilledXuanPaperItem extends Item implements ITabItem {
    public FilledXuanPaperItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .setNoRepair());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);

        var tag = stack.getOrCreateTag();
        if (!tag.contains(SCAConstants.DRAWING_TAG_NAME)) {
            return;
        }

        var nbt = tag.getCompound(SCAConstants.DRAWING_TAG_NAME);
        var drawing = DrawingDataVersion.getLatest().fromTag(nbt);

        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_DRAWING_AUTHOR_KEY, drawing.getAuthor().getString()));

        var date = drawing.getZonedDate();
        tooltip.add(Component.translatable(SCAConstants.TRANSLATE_DRAWING_DATE_KEY, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond()));
    }

    @Override
    public Component getName(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (!tag.contains(SCAConstants.DRAWING_TAG_NAME)) {
            return super.getName(stack);
        }

        var nbt = tag.getCompound(SCAConstants.DRAWING_TAG_NAME);
        var drawing = DrawingDataVersion.getLatest().fromTag(nbt);

        return drawing.getTitle();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return XuanPaperRenderer.getInstance();
            }
        });
    }

    @Override
    public List<ResourceLocation> getTabs() {
        return List.of();
    }
}
