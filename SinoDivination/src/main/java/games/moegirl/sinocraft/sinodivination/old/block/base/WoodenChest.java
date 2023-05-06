package games.moegirl.sinocraft.sinodivination.old.block.base;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.old.blockentity.WoodenChestEntity;
import games.moegirl.sinocraft.sinodivination.old.item.WoodenChestItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WoodenChest extends ChestBlock implements ILootableBlock {

    private static final List<WoodenChest> CHESTS = new LinkedList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(WoodenChest.class);

    public static Stream<WoodenChest> stream() {
        return CHESTS.stream();
    }

    public final ResourceLocation name;
    public final boolean isTrapped;

    public final RegistryObject<BlockEntityType<? extends WoodenChestEntity>> entity;
    public final RegistryObject<? extends WoodenChestItem> item;

    private final Supplier<? extends Block> planks;

    public WoodenChest(Tree tree, RegistryObject<BlockEntityType<? extends WoodenChestEntity>> entity,
                       RegistryObject<? extends WoodenChestItem> item) {
        super(Properties.copy(Blocks.CHEST)/*.color(woodwork.plankColor)*/, entity::get);
        this.name = tree.name;
        this.planks = tree.getBlockObj(TreeBlockType.PLANKS);
        this.entity = entity;
        this.item = item;
        this.isTrapped = false;

        CHESTS.add(this);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return blockEntityType.get().create(pPos, pState);
    }

    @Override
    protected Stat<ResourceLocation> getOpenChestStat() {
        if (isTrapped) {
            return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
        }
        return super.getOpenChestStat();
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return isTrapped || super.isSignalSource(pState);
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (isTrapped) {
            return Mth.clamp(ChestBlockEntity.getOpenCount(pLevel, pPos), 0, 15);
        }
        return super.getSignal(pState, pLevel, pPos, pDirection);
    }

    @Override
    public int getDirectSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (isTrapped) {
            return pDirection == Direction.UP ? pState.getSignal(pLevel, pPos, pDirection) : 0;
        }
        return super.getDirectSignal(pState, pLevel, pPos, pDirection);
    }

    public BlockEntityType<? extends WoodenChestEntity> entity() {
        return entity.get();
    }

    public WoodenChestItem item() {
        return item.get();
    }

    public Block planks() {
        return planks.get();
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.createNameableBlockEntityTable(this);
    }

    public void verifyTexture(ExistingFileHelper helper) {
        String prefix = "textures/entity/chest/" + name.getPath();
        ResourceLocation main = new ResourceLocation(name.getNamespace(), prefix + ".png");
        if (!helper.exists(main, PackType.CLIENT_RESOURCES)) {
            LOGGER.warn("Not found chest skin at {}", main);
        }
        ResourceLocation left = new ResourceLocation(name.getNamespace(), prefix + "_left.png");
        if (!helper.exists(left, PackType.CLIENT_RESOURCES)) {
            LOGGER.warn("Not found chest skin at {}", left);
        }
        ResourceLocation right = new ResourceLocation(name.getNamespace(), prefix + "_right.png");
        if (!helper.exists(right, PackType.CLIENT_RESOURCES)) {
            LOGGER.warn("Not found chest skin at {}", right);
        }
    }
}
