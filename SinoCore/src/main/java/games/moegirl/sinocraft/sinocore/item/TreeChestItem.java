package games.moegirl.sinocraft.sinocore.item;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.client.TreeChestItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author luqin2007
 */
public class TreeChestItem extends BlockItem {

    private final Tree tree;
    private final TreeBlockType block;

    public TreeChestItem(Properties properties, Tree tree, TreeBlockType block) {
        super(tree.getBlock(block), properties);

        this.tree = tree;
        this.block = block;

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
        consumer.accept(new TreeChestItemRenderer(block, tree));
    }
}

/**
 * 发射器
 */
class ChestDispenseBehavior extends OptionalDispenseItemBehavior {

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
