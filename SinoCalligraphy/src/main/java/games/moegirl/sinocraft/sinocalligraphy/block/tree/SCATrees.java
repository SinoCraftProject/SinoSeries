package games.moegirl.sinocraft.sinocalligraphy.block.tree;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import games.moegirl.sinocraft.sinocore.event.BlockStrippingEvent;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import games.moegirl.sinocraft.sinofoundation.item.SinoSeriesTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class SCATrees {
    public static final Tree GREEN_SANDALWOOD = Tree.builder(new ResourceLocation(SinoCalligraphy.MODID, "green_sandalwood"))
            .translate("zh_cn", "青檀木")
            .translate("zh_tw", "青檀木")
            .translate("zh_hk", "青檀木")
            .translate("en_us", "Green Sandalwood")
            .tab(SinoSeriesTabs.BUILDING_BLOCKS)
            .tab(TreeBlockType.SAPLING, SinoSeriesTabs.AGRICULTURE)
            .build();

    public static void register(IEventBus bus) {
        TreeRegistry.register(SinoCalligraphy.MODID, bus);

        BlockStrippingEvent.registerStripping(TreeBlockType.LOG.makeResourceLoc(GREEN_SANDALWOOD.getName()),
                GREEN_SANDALWOOD.getBlockObj(TreeBlockType.LOG),
                GREEN_SANDALWOOD.getBlockObj(TreeBlockType.STRIPPED_LOG),
                SCAItems.GREEN_SANDALWOOD_BARK::get);
        BlockStrippingEvent.registerStripping(TreeBlockType.LOG_WOOD.makeResourceLoc(GREEN_SANDALWOOD.getName()),
                GREEN_SANDALWOOD.getBlockObj(TreeBlockType.LOG_WOOD),
                GREEN_SANDALWOOD.getBlockObj(TreeBlockType.STRIPPED_LOG_WOOD),
                SCAItems.GREEN_SANDALWOOD_BARK::get);
    }
}
