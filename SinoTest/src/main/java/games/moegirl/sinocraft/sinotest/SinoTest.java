package games.moegirl.sinocraft.sinotest;

import games.moegirl.sinocraft.sinotest.sinocore.TestSinoCore;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

@Mod(SinoTest.MODID)
public class SinoTest {
    public static final String MODID = "sinotest";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, "net"), () -> "1.0", s -> true, s -> true);
    public static final AtomicInteger NET_ID = new AtomicInteger();

    public SinoTest() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        for (Field field : getClass().getFields()) {
            if (field.getType() != DeferredRegister.class) continue;
            try {
                ((DeferredRegister<?>) field.get(null)).register(bus);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        new TestSinoCore().register(bus);
    }
}
