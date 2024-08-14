package games.moegirl.sinocraft.sinocore.event.game.args.crafting;

import games.moegirl.sinocraft.sinocore.event.CancellableEventArgsBase;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CartographyTableCraftEventArgs extends CancellableEventArgsBase {
    @NotNull
    private final ItemStack mapInput;
    @NotNull
    private final ItemStack otherInput;
    @NotNull
    private ItemStack output;

    public CartographyTableCraftEventArgs(@NotNull ItemStack mapInput, @NotNull ItemStack otherInput, @NotNull ItemStack output) {
        this.mapInput = mapInput;
        this.otherInput = otherInput;
        this.output = output;
    }

    @NotNull
    public ItemStack getMapInput() {
        return mapInput;
    }

    @NotNull
    public ItemStack getOtherInput() {
        return otherInput;
    }

    @NotNull
    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(@NotNull ItemStack output) {
        this.output = output;
    }
}
