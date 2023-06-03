package games.moegirl.sinocraft.sinocalligraphy.block;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.fluid.SCAFluids;
import games.moegirl.sinocraft.sinocore.item.tab.TabsRegistry;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCABlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoCalligraphy.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final RegistryObject<LiquidBlock> WOOD_PULP_BLOCK = BLOCKS.register("wood_pulp", () -> new LiquidBlock(SCAFluids.WOOD_PULP, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryObject<Block> PAPER_DRYING_RACK = BLOCKS.register("paper_drying_rack", PaperDryingRackBlock::new);
}
