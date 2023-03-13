package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;

import static games.moegirl.sinocraft.sinotest.SinoTest.MODID;

public class TestSinoCore {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static Tree tree = Tree.builder(new ResourceLocation(SinoTest.MODID, "test"), "测试树")
            .grower(new ResourceLocation(SinoTest.MODID, "test_tree"))
            .build(BLOCKS, ITEMS);

    public void register(IEventBus bus) {
        Tree.register(SinoTest.MODID, bus);
        for (Field field : getClass().getFields()) {
            if (field.getType() != DeferredRegister.class) continue;
            try {
                ((DeferredRegister<?>) field.get(null)).register(bus);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
