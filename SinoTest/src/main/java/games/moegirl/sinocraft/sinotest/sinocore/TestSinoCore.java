package games.moegirl.sinocraft.sinotest.sinocore;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.utility.NetworkHolder;
import games.moegirl.sinocraft.sinocore.woodwork.Woodwork;
import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

import static games.moegirl.sinocraft.sinotest.SinoTest.*;

public class TestSinoCore {

    public static final NetworkHolder NETWORK = NetworkHolder.simple(CHANNEL, NET_ID);

    public static Tree tree = Tree.builder(new ResourceLocation(MODID, "test"), "测试树")
            .grower(new ResourceLocation(SinoTest.MODID, "test_tree"))
            .build(BLOCKS, ITEMS);

    public static Woodwork woodwork = Woodwork.builder(new ResourceLocation(MODID, "test"), "测试")
            .build(tree.log, ITEMS, BLOCKS, BLOCK_ENTITIES);

    public void register(IEventBus bus) {
        Tree.register(MODID, bus);
        Woodwork.register(MODID, bus, NETWORK);
    }
}
