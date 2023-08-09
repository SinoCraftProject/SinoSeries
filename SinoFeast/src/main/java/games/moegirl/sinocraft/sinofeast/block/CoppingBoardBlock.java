package games.moegirl.sinocraft.sinofeast.block;

import games.moegirl.sinocraft.sinocore.block.AbstractEntityBlock;
import games.moegirl.sinocraft.sinofeast.block.entity.CoppingBoardBlockEntity;
import games.moegirl.sinocraft.sinofeast.block.entity.SFBlockEntities;
import games.moegirl.sinocraft.sinofeast.item.SFItems;
import games.moegirl.sinocraft.sinofeast.utility.IFood;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class CoppingBoardBlock extends AbstractEntityBlock<CoppingBoardBlockEntity> {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 2, 14);

    public CoppingBoardBlock() {
        super(Properties.of(), SFBlockEntities.COPPING_BOARD_BLOCK_ENTITY);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        //TODO WIP
        if (hand == InteractionHand.MAIN_HAND) {

            ItemStack itemInHand = player.getItemInHand(hand);
            if (state.getBlock() instanceof CoppingBoardBlock coppingBoardBlock) {
                CoppingBoardBlockEntity blockEntity = coppingBoardBlock.getBlockEntity(level, pos);
                IItemHandler iItemHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(RuntimeException::new);
                ItemStack inFood = iItemHandler.getStackInSlot(CoppingBoardBlockEntity.INPUT);
                if (itemInHand.is(SFItems.KITCHEN_KNIFE.get())) {

                    ItemStack copy = inFood.copy();
                    inFood.setCount(inFood.getCount() - 1);
                    copy.setCount(1);
                    copy.addTagElement("shred", ByteTag.valueOf(true));
                    iItemHandler.insertItem(CoppingBoardBlockEntity.OUTPUT, copy, false);

                } else if (itemInHand.isEmpty()) {
                    if (player.isShiftKeyDown()) {
                        ItemStack outItem = iItemHandler.extractItem(CoppingBoardBlockEntity.OUTPUT, 64, false);
                        if (!outItem.isEmpty()) {
                            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), outItem));
                        }
                    } else {
                        ItemStack inItem = iItemHandler.extractItem(CoppingBoardBlockEntity.INPUT, 64, false);
                        if (!inItem.isEmpty()) {
                            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), inItem));
                        }
                    }


                } else if (itemInHand.getItem() instanceof IFood){
                    player.setItemInHand(hand, iItemHandler.insertItem(CoppingBoardBlockEntity.INPUT, itemInHand, false));
                }
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }


}
