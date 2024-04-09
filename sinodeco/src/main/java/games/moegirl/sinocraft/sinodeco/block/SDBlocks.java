package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class SDBlocks {
    public static IRegistry<Block> BLOCKS = RegistryManager.obtain(SinoDeco.MODID, Registries.BLOCK);

    public static void register() {
    }

    public static IRegRef<Block, HorizontalDirectionalBlock> WOOD_DESK = BLOCKS.register("wood_desk", WoodDeskBlock::new);
}
