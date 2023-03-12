package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.tree.Tree;
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

    Tree tree = Tree.builder("test").build(BLOCKS, ITEMS);

    public void register(IEventBus bus) {
        tree.registerAll(bus);
        for (Field field : getClass().getFields()) {
            try {
                ((DeferredRegister<?>) field.get(null)).register(bus);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
