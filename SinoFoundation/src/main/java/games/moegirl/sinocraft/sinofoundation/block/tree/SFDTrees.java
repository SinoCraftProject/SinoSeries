package games.moegirl.sinocraft.sinofoundation.block.tree;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class SFDTrees {
    public static final Tree MULBERRY = Tree.builder(new ResourceLocation(SinoFoundation.MODID, "mulberry"))
            .translate("zh_cn", "桑树")
            .translate("zh_tw", "桑樹")
            .translate("zh_hk", "桑樹")
            .translate("lzh", "桑")
            .translate("en_us", "Mulberry")
            .tab(SinoSeriesTabs.BUILDING_BLOCKS)
            .tab(TreeBlockType.SAPLING, SinoSeriesTabs.AGRICULTURE)
            .build();

    public static void register(IEventBus bus) {
        TreeRegistry.register(SinoFoundation.MODID, bus);
    }
}
