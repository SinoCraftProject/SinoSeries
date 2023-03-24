package games.moegirl.sinocraft.sinotest;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SinoTest.MODID)
public class SinoTest {
    public static final String MODID = "sinotest";

    public static Tree TREE = Tree.builder(new ResourceLocation(MODID, "test"))
            .translate("zh_cn", "测试")
            .build();

    static {
        TreeRegistry.addTree(TREE);
    }

    public SinoTest() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        TreeRegistry.register(MODID, bus);
    }
}
