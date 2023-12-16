package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinocore.data.gen.AbstructLootTableProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.data.PackOutput;

import java.util.List;

public class SFDBlockLootTableProvider extends AbstructLootTableProvider {
    public SFDBlockLootTableProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    public void getTables(List<SubProviderEntry> tables) {
        // Todo: qyl27: jade drop.
        addBlocks(SFDBlocks.BLOCKS);
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
}
