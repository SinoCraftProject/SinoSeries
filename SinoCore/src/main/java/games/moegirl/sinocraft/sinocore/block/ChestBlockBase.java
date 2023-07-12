package games.moegirl.sinocraft.sinocore.block;

import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
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

/**
 * 箱子
 */
public class ChestBlockBase extends ChestBlock implements ILootableBlock {

    public final ResourceLocation name;

    public ChestBlockBase(Properties properties, RegistryObject<BlockEntityType<? extends ChestBlockEntity>> blockEntity, ResourceLocation name) {
        super(properties, blockEntity);
        this.name = name;
    }

    public ChestBlockBase(RegistryObject<BlockEntityType<? extends ChestBlockEntity>> blockEntity, ResourceLocation name) {
        this(Properties.copy(Blocks.CHEST), blockEntity, name);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return blockEntityType.get().create(pPos, pState);
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.createNameableBlockEntityTable(this);
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
