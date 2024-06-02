package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SDBlocks {
    public static IRegistry<Block> BLOCKS = RegistryManager.obtain(SinoDeco.MODID, Registries.BLOCK);

    public static void register() {
    }

    public static Supplier<Block> WOOD_DESK = BLOCKS.register("wood_desk", WoodDeskBlock::new);
}
