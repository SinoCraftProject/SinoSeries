package games.moegirl.sinocraft.sinocore.client.component;

import net.minecraft.client.gui.components.Button;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public class ButtonOptional {

    public static ButtonOptional wrap(Optional<ImageButton> opt) {
        return new ButtonOptional(opt);
    }

    private final Optional<ImageButton> button;

    public ButtonOptional(Optional<ImageButton> button) {
        this.button = button;
    }

    public boolean isPresent() {
        return button.isPresent();
    }

    public ImageButton orElse(Supplier<ImageButton> supplier) {
        return button.orElseGet(supplier);
    }

    @Nullable
    public ImageButton orNull() {
        return button.orElse(null);
    }

    public ButtonOptional onRightClick(@Nullable Button.OnPress onRightClick) {
        button.ifPresent(btn -> btn.setOnRightClick(onRightClick));
        return this;
    }

    public ButtonOptional onLeftClick(@Nullable Button.OnPress onLeftClick) {
        button.ifPresent(btn -> btn.setOnLeftClick(onLeftClick));
        return this;
    }
}
