package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.utility.NetworkHolder;
import net.minecraftforge.eventbus.api.IEventBus;

import static games.moegirl.sinocraft.sinotest.SinoTest.*;

public class TestSinoCore {

    public static final NetworkHolder NETWORK = NetworkHolder.simple(CHANNEL, NET_ID);

//    public static Tree tree = Tree.builder(MODID, "test")
//            .zh_cn("测试树")
//            .grower(new ResourceLocation(SinoTest.MODID, "test_tree"))
//            .build(BLOCKS, ITEMS);
//
//    public static Woodwork woodwork = Woodwork.builder(MODID, "test")
//            .zh_cn("测试")
//            .build(tree.log, ITEMS, BLOCKS, BLOCK_ENTITIES);
//
    public void register(IEventBus bus) {
//        Tree.register(MODID, bus);
//        Woodwork.register(MODID, bus, NETWORK);
    }
}
