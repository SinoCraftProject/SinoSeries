package games.moegirl.sinocraft.sinocore.item;

import games.moegirl.sinocraft.sinocore.client.item.BaseChestItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public abstract class BaseChestItem extends BlockItem {

    public static BaseChestItem create(Properties properties, Supplier<? extends Block> block, Supplier<? extends BlockEntityType<?>> be) {
        return new BaseChestItem(properties, block) {
            @Override
            protected BlockEntityType<?> getBlockEntityType() {
                return be.get();
            }
        };
    }

    public static BaseChestItem create(Supplier<? extends Block> block, Supplier<? extends BlockEntityType<?>> be) {
        return create(new Properties(), block, be);
    }

    protected BaseChestItem(Properties properties, Supplier<? extends Block> block) {
        super(block.get(), properties);
        // 发射器
        DispenserBlock.registerBehavior(this, new ChestDispenseBehavior());
    }

    // 燃烧时间
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return ForgeHooks.getBurnTime(new ItemStack(Items.CHEST), recipeType);
    }

    // 物品渲染
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new BaseChestItemRenderer(this::getBlock, this::getBlockEntityType));
    }

    /**
     * initializeClient 会在父类构造调用
     * @return be
     */
    protected abstract BlockEntityType<?> getBlockEntityType();

    /**
     * 发射器
     */
    static class ChestDispenseBehavior extends OptionalDispenseItemBehavior {

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            for (AbstractChestedHorse horse : source.getLevel().getEntitiesOfClass(AbstractChestedHorse.class, new AABB(blockpos), h -> h.isAlive() && !h.hasChest())) {
                if (!horse.isTamed() || !horse.getSlot(499).set(stack)) continue;
                stack.shrink(1);
                this.setSuccess(true);
                return stack;
            }
            return super.execute(source, stack);
        }
    }
}
