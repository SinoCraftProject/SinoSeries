package games.moegirl.sinocraft.sinocore.event.game.args;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CartographyCraftEventArgs {
    private ItemStack inputMap;
    private ItemStack inputOther;
    private ItemStack output;
    private MapItemSavedData mapToRender;

    public CartographyCraftEventArgs(ItemStack inputMap, ItemStack inputOther, ItemStack output, MapItemSavedData mapToRender) {
        this.inputMap = inputMap;
        this.inputOther = inputOther;
        this.output = output;
        this.mapToRender = mapToRender;
    }

    public ItemStack getInputMap() {
        return inputMap;
    }

    public void setInputMap(ItemStack inputMap) {
        this.inputMap = inputMap;
    }

    public ItemStack getInputOther() {
        return inputOther;
    }

    public void setInputOther(ItemStack inputOther) {
        this.inputOther = inputOther;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    public MapItemSavedData getMapToRender() {
        return mapToRender;
    }

    public void setMapToRender(MapItemSavedData mapToRender) {
        this.mapToRender = mapToRender;
    }
}
