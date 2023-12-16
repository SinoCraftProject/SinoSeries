package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstructLootTableProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantBlock;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;

import java.util.List;

public class SFDBlockLootTableProvider extends AbstructLootTableProvider {
    public SFDBlockLootTableProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    public void getTables(List<SubProviderEntry> tables) {
        addBlock(SFDBlocks.GREEN_RADISH_PLANT, table(dropSeeds(SFDBlockItems.GREEN_RADISH.get()), dropWhenMature(SFDBlocks.GREEN_RADISH_PLANT.get(), SFDBlockItems.GREEN_RADISH.get(), 0, 4)));
        addBlock(SFDBlocks.SUMMER_RADISH_PLANT, table(dropSeeds(SFDBlockItems.SUMMER_RADISH.get()), dropWhenMature(SFDBlocks.SUMMER_RADISH_PLANT.get(), SFDBlockItems.SUMMER_RADISH.get(), 0, 4)));
        addBlock(SFDBlocks.WHITE_RADISH_PLANT, table(dropSeeds(SFDBlockItems.WHITE_RADISH.get()), dropWhenMature(SFDBlocks.WHITE_RADISH_PLANT.get(), SFDBlockItems.WHITE_RADISH.get(), 0, 4)));
        addBlock(SFDBlocks.CHILI_PEPPER_PLANT, table(dropWhenNotMature(SFDBlocks.CHILI_PEPPER_PLANT.get(), SFDBlockItems.CHILI_PEPPER_SEED.get()), dropWhenMature(SFDBlocks.CHILI_PEPPER_PLANT.get(), SFDItems.CHILI_PEPPER.get(), 2, 4)));
        addBlock(SFDBlocks.GREEN_PEPPER_PLANT, table(dropWhenNotMature(SFDBlocks.GREEN_PEPPER_PLANT.get(), SFDBlockItems.GREEN_PEPPER_SEED.get()), dropWhenMature(SFDBlocks.GREEN_PEPPER_PLANT.get(), SFDItems.GREEN_PEPPER.get(), 2, 4)));
        addBlock(SFDBlocks.EGGPLANT_PLANT, table(dropWhenNotMature(SFDBlocks.EGGPLANT_PLANT.get(), SFDBlockItems.EGGPLANT_SEED.get()), dropWhenMature(SFDBlocks.EGGPLANT_PLANT.get(), SFDItems.EGGPLANT.get(), 2, 5)));
        addBlock(SFDBlocks.CABBAGE_PLANT, table(dropWhenNotMature(SFDBlocks.CABBAGE_PLANT.get(), SFDBlockItems.CABBAGE_SEED.get()), dropWhenMature(SFDBlocks.CABBAGE_PLANT.get(), SFDItems.CABBAGE.get(), 2, 5)));
        addBlock(SFDBlocks.MILLET_PLANT, table(dropWhenNotMature(SFDBlocks.MILLET_PLANT.get(), SFDBlockItems.MILLET_SEED.get()), dropWhenMature(SFDBlocks.MILLET_PLANT.get(), SFDBlockItems.MILLET_SEED.get(), 2, 6)));
        addBlock(SFDBlocks.SOYBEAN_PLANT, table(dropSeeds(SFDBlockItems.SOYBEAN.get()), dropWhenMature(SFDBlocks.SOYBEAN_PLANT.get(), SFDBlockItems.SOYBEAN.get(), 1, 4)));
        addBlock(SFDBlocks.GARLIC_PLANT, table(dropSeeds(SFDBlockItems.GARLIC.get()), dropWhenMature(SFDBlocks.GARLIC_PLANT.get(), SFDBlockItems.GARLIC.get(), 2, 4)));

        // Todo: qyl27: jade drop.
        addBlock(SFDBlocks.BLACK_JADE_ORE, table(dropIngotByChance(SFDItems.BLACK_JADE.get(), 1, 1)));
        addBlock(SFDBlocks.DEEPSLATE_BLACK_JADE_ORE, table(dropIngotByChance(SFDItems.BLACK_JADE.get(), 1, 1)));
        addBlock(SFDBlocks.GREEN_JADE_ORE, table(dropIngotByChance(SFDItems.GREEN_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.DEEPSLATE_GREEN_JADE_ORE, table(dropIngotByChance(SFDItems.GREEN_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.RED_JADE_ORE, table(dropIngotByChance(SFDItems.RED_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.DEEPSLATE_RED_JADE_ORE, table(dropIngotByChance(SFDItems.RED_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.WHITE_JADE_ORE, table(dropIngotByChance(SFDItems.WHITE_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.DEEPSLATE_WHITE_JADE_ORE, table(dropIngotByChance(SFDItems.WHITE_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.YELLOW_JADE_ORE, table(dropIngotByChance(SFDItems.YELLOW_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.DEEPSLATE_YELLOW_JADE_ORE, table(dropIngotByChance(SFDItems.YELLOW_JADE.get(), 1, 2)));
        addBlock(SFDBlocks.NITER_ORE, table(dropIngotByChance(SFDItems.NITER.get(), 1, 2)));
        addBlock(SFDBlocks.SULPHUR_ORE, table(dropIngotByChance(SFDItems.SULPHUR.get(), 1, 2)));
    }

    protected LootPool.Builder dropWhenMature(PlantBlock block, ItemLike item, int minDrop, int maxDrop) {
        return dropWhenIntPropertyByChance(block, item, block.getStageProperty(), block.getMaxAge(), minDrop, maxDrop);
    }

    protected LootPool.Builder dropWhenNotMature(PlantBlock block, ItemLike item) {
        return dropWhenNotIntPropertyByChance(block, item, block.getStageProperty(), block.getMaxAge(), 1, 1);
    }
}
