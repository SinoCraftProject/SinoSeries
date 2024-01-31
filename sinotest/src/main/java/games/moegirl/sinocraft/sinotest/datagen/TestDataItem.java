package games.moegirl.sinocraft.sinotest.datagen;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TestDataItem extends Item {

    public static IRegRef<Item, TestDataItem> TEST_ITEM_DATA;

    public static void registerAll() {
        TEST_ITEM_DATA = TestRegistry.ITEMS.register("test_data", TestDataItem::new);
    }

    public TestDataItem() {
        super(new Properties().sino$tab(TestRegistry.TEST_TAB));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide()) {
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
            if (offHand.is(TestTags.TEST_ITEM_TAG)) {
                player.displayClientMessage(Component.translatable(TestLangKeys.TEST_WITH_ITEM), true);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player != null && !level.isClientSide()) {
            if (level.getBlockState(context.getClickedPos()).is(TestTags.TEST_BLOCK_TAG)) {
                player.displayClientMessage(Component.translatable(TestLangKeys.TEST_WITH_BLOCK), true);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
