package games.moegirl.sinocraft.sinocore.tree.event.data;

import games.moegirl.sinocraft.sinocore.data.model.AbstractBlockStateProvider;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class ProviderBlockState extends AbstractBlockStateProvider {

    protected final List<Tree> treeTypes;

    public ProviderBlockState(PackOutput output, String modid, ExistingFileHelper exHelper, List<Tree> treeTypes) {
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

            logBlock(tree.getBlock(TreeBlockType.LOG));
            logBlock(tree.getBlock(TreeBlockType.STRIPPED_LOG));
            simpleBlock(tree.getBlock(TreeBlockType.LOG_WOOD));
            simpleBlock(tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD));

            simpleBlock(tree.getBlock(TreeBlockType.LEAVES), models()
                    .cubeAll(TreeBlockType.LEAVES.makeResourceLoc(tree.getName()).getPath(),
                            blockLoc(TreeBlockType.LEAVES.makeResourceLoc(tree.getName())))
                    .renderType("cutout_mipped"));

            simpleBlock(tree.getBlock(TreeBlockType.SAPLING), models()
                    .cross(TreeBlockType.SAPLING.makeResourceLoc(tree.getName()).getPath(),
                            blockLoc(TreeBlockType.SAPLING.makeResourceLoc(tree.getName())))
                    .renderType("cutout"));
            simpleBlock(tree.getBlock(TreeBlockType.POTTED_SAPLING), models()
                    .singleTexture(TreeBlockType.POTTED_SAPLING.makeResourceLoc(tree.getName()).getPath(),
                            blockLoc(mcLoc("flower_pot_cross")),
                            blockLoc(TreeBlockType.SAPLING.makeResourceLoc(tree.getName())))
                    .renderType("cutout"));

            simpleBlock(tree.getBlock(TreeBlockType.PLANKS));
            ResourceLocation planksTextures = blockTexture(tree.getBlock(TreeBlockType.PLANKS));

            ModelFile sign = models().sign(TreeBlockType.SIGN.makeResourceLoc(tree.getName()).getPath(), planksTextures);
            signBlock(tree.getBlock(TreeBlockType.SIGN),
                    tree.getBlock(TreeBlockType.WALL_SIGN), sign);
            simpleBlock(tree.getBlock(TreeBlockType.HANGING_SIGN), sign);
            simpleBlock(tree.getBlock(TreeBlockType.WALL_HANGING_SIGN), sign);

            ResourceLocation trapdoorName = TreeBlockType.TRAPDOOR.makeResourceLoc(tree.getName());
            ResourceLocation trapdoorTextures = new ResourceLocation(trapdoorName.getNamespace(),
                    ModelProvider.BLOCK_FOLDER + "/" + trapdoorName.getPath());
            trapdoorBlockWithRenderType(tree.getBlock(TreeBlockType.TRAPDOOR), trapdoorTextures, true, "cutout_mipped");

            slabBlock(tree.getBlock(TreeBlockType.SLAB), planksTextures, planksTextures);
            stairsBlock(tree.getBlock(TreeBlockType.STAIRS), planksTextures);

            fenceBlock(tree.getBlock(TreeBlockType.FENCE), planksTextures);
            models().fenceInventory(TreeBlockType.FENCE.makeResourceLoc(tree.getName()).getPath() + "_inventory", planksTextures);
            fenceGateBlock(tree.getBlock(TreeBlockType.FENCE_GATE), planksTextures);

            buttonBlock(tree.getBlock(TreeBlockType.BUTTON), planksTextures);
            models().singleTexture(TreeBlockType.BUTTON.makeResourceLoc(tree.getName()).getPath() + "_inventory", blockLoc(mcLoc("button_inventory")), planksTextures);

            pressurePlateBlock(tree.getBlock(TreeBlockType.PRESSURE_PLATE), planksTextures);

            DoorBlock door = tree.getBlock(TreeBlockType.DOOR);
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
            doorBlockWithRenderType(door, doorBottom, doorTop, "cutout_mipped");
        }
    }
}
