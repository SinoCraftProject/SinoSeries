package games.moegirl.sinocraft.sinotest.sinocore.texture;

import games.moegirl.sinocraft.sinocore.utility.texture.SlotStrategy;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureMap;
import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static games.moegirl.sinocraft.sinotest.SinoTest.MODID;

/**
 * @author luqin2007
 */
public class TestTextureMenu extends AbstractContainerMenu {

    public static final TextureMap TEXTURE = TextureMap.of(new ResourceLocation(MODID, "textures/gui/smoker.png"));

    public Container container = new SimpleContainer(3);

    public TestTextureMenu(int id, Inventory inventory) {
        super(SinoTest.TEST_TEXTURE_MENU.get(), id);

        TEXTURE.placeSlot(this, container, "input", 0, SlotStrategy.insertFilter(Items.BAMBOO));
        TEXTURE.placeSlot(this, container, "matel", 1, SlotStrategy.insertFilter(Items.COAL));
        TEXTURE.placeSlot(this, container, "output", 1, SlotStrategy.onlyTake());

        TEXTURE.placeSlots(this, inventory, "inventory", 0,  SlotStrategy.simple());
        TEXTURE.placeSlots(this, inventory, "selection", 27,  SlotStrategy.simple());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
