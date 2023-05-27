package games.moegirl.sinocraft.sinocore.tree.client;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

/**
 * @author luqin2007
 */
public class TreeChestRenderer extends ChestRenderer<ChestBlockEntity> {

    private final Material left, right, single;

    public TreeChestRenderer(BlockEntityRendererProvider.Context context, Tree tree, boolean isTrapped) {
        super(context);
        ResourceLocation name = tree.name;
        if (isTrapped) {
            left = new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), "trapped_" + name.getPath() + "_left"));
            right = new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), "trapped_" + name.getPath() + "_right"));
            single = new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), "trapped_" + name.getPath()));
        } else {
            left = new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), name.getPath() + "_left"));
            right = new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), name.getPath() + "_right"));
            single = new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), name.getPath()));
        }
    }

    @Override
    protected Material getMaterial(ChestBlockEntity blockEntity, ChestType chestType) {
        if (xmasTextures) {
            return super.getMaterial(blockEntity, chestType);
        }
        if (chestType == ChestType.LEFT) {
            return left;
        } else if (chestType == ChestType.RIGHT) {
            return right;
        } else {
            return single;
        }
    }
}
