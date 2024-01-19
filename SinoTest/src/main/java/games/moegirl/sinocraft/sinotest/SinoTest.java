package games.moegirl.sinocraft.sinotest;

import com.mojang.logging.LogUtils;
import games.moegirl.sinocraft.sinocore.registry.IRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinotest.network.TestNetwork;
import games.moegirl.sinocraft.sinotest.registry.TestRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;

public class SinoTest {
    public static final String MODID = "sinocore";

    public static void registerAll() {
        TestRegistry.registerAll();
        TestNetwork.registerAll();
    }
}
