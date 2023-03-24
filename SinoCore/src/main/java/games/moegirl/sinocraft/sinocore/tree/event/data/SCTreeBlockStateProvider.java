package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractBlockStateProvider;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SCTreeBlockStateProvider extends AbstractBlockStateProvider {

    protected final List<Tree> treeTypes;

    public SCTreeBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exHelper, List<Tree> treeTypes) {
        super(output, modid, exHelper);

        this.treeTypes = treeTypes;
    }

    @Override
    public @NotNull String getName() {
        return "Tree " + super.getName();
    }

    @Override
    protected void registerStatesAndModels() {
        for (var tree : treeTypes) {
            simpleBlock(tree.getBlock(TreeBlockType.SAPLING));

            logBlock((RotatedPillarBlock) tree.getBlock(TreeBlockType.LOG));
            logBlock((RotatedPillarBlock) tree.getBlock(TreeBlockType.STRIPPED_LOG));

            simpleBlock(tree.getBlock(TreeBlockType.LOG_WOOD));
            simpleBlock(tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD));

            simpleBlock(tree.getBlock(TreeBlockType.LEAVES));
            simpleBlock(tree.getBlock(TreeBlockType.PLANKS));

            simpleBlock(tree.getBlock(TreeBlockType.POTTED_SAPLING),
                    models().singleTexture(TreeBlockType.POTTED_SAPLING.makeResourceLoc(tree.getName()).getPath(),
                            blockLoc(mcLoc("/flower_pot_cross")),
                            blockLoc(TreeBlockType.SAPLING.makeResourceLoc(tree.getName()))));

            ResourceLocation planksTextures = blockTexture(tree.getBlock(TreeBlockType.PLANKS));

            ModelFile sign = models().sign(TreeBlockType.SIGN.makeResourceLoc(tree.getName()).getPath(), planksTextures);
            signBlock((StandingSignBlock) tree.getBlock(TreeBlockType.SIGN),
                    (WallSignBlock) tree.getBlock(TreeBlockType.WALL_SIGN), sign);

            trapdoorBlock((TrapDoorBlock) tree.getBlock(TreeBlockType.TRAPDOOR), planksTextures, true);
            slabBlock((SlabBlock) tree.getBlock(TreeBlockType.SLAB), planksTextures, planksTextures);
            stairsBlock((StairBlock) tree.getBlock(TreeBlockType.STAIRS), planksTextures);

            fenceBlock((FenceBlock) tree.getBlock(TreeBlockType.FENCE), planksTextures);
            fenceGateBlock((FenceGateBlock) tree.getBlock(TreeBlockType.FENCE_GATE), planksTextures);

            buttonBlock((ButtonBlock) tree.getBlock(TreeBlockType.BUTTON), planksTextures);
            pressurePlateBlock((PressurePlateBlock) tree.getBlock(TreeBlockType.PRESSURE_PLATE), planksTextures);

            DoorBlock door = (DoorBlock) tree.getBlock(TreeBlockType.DOOR);
            ResourceLocation doorName = TreeBlockType.DOOR.makeResourceLoc(tree.getName());
            ResourceLocation doorTop = new ResourceLocation(doorName.getNamespace(),
                    ModelProvider.BLOCK_FOLDER + "/" + doorName.getPath() + "_top");
            ResourceLocation doorBottom = new ResourceLocation(doorName.getNamespace(),
                    ModelProvider.BLOCK_FOLDER + "/" + doorName.getPath() + "_bottom");

            var exHelper = models().existingFileHelper;
            var resType = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");
            if (!exHelper.exists(doorTop, resType)) {
                logger.warn(doorTop + " does not exist, use " + planksTextures);
                doorTop = planksTextures;
            }
            if (!exHelper.exists(doorBottom, resType)) {
                logger.warn(doorBottom + " does not exist, use " + planksTextures);
                doorBottom = planksTextures;
            }
            doorBlock(door, doorBottom, doorTop);

            models().singleTexture(TreeBlockType.BUTTON.makeResourceLoc(tree.getName()).getPath() + "_inventory", blockLoc(mcLoc("/button_inventory")), planksTextures);
            models().fenceInventory(TreeBlockType.FENCE.makeResourceLoc(tree.getName()).getPath() + "_inventory", planksTextures);
        }
    }
}
