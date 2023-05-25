package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * 箱子
 */
public class BaseChestBlock extends ChestBlock {

    public final ResourceLocation name;

    public BaseChestBlock(Properties properties, Tree tree) {
        super(properties, () -> tree.getBlockEntityType(TreeBlockType.CHEST));
        this.name = tree.name;
    }

    public BaseChestBlock(Properties properties, Tree tree, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, supplier);
        this.name = tree.name;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return blockEntityType.get().create(pPos, pState);
    }

    public void verifyTexture(ExistingFileHelper helper, Logger logger) {
        String prefix = "textures/entity/chest/" + name.getPath();
        ResourceLocation main = new ResourceLocation(name.getNamespace(), prefix + ".png");
        if (!helper.exists(main, PackType.CLIENT_RESOURCES)) {
            logger.warn("Not found chest skin at {}", main);
        }
        ResourceLocation left = new ResourceLocation(name.getNamespace(), prefix + "_left.png");
        if (!helper.exists(left, PackType.CLIENT_RESOURCES)) {
            logger.warn("Not found chest skin at {}", left);
        }
        ResourceLocation right = new ResourceLocation(name.getNamespace(), prefix + "_right.png");
        if (!helper.exists(right, PackType.CLIENT_RESOURCES)) {
            logger.warn("Not found chest skin at {}", right);
        }
    }
}
