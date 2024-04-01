package games.moegirl.sinocraft.sinodeco.block.item;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public class SDBlocks {
    public static IRegistry<Block> BLOCKS = RegistryManager.obtain(SinoDeco.MODID, Registries.BLOCK);

    public static void register() {
        BLOCKS.register();
    }
}
