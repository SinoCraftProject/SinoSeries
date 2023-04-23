package games.moegirl.sinocraft.sinocore.client.component;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author luqin2007
 */
public class EditBoxOptional {

    public static EditBoxOptional wrap(AbstractContainerScreen<?> parent, Optional<EditBox> opt) {
        return new EditBoxOptional(parent, opt);
    }

    private final AbstractContainerScreen<?> parent;

    private final Optional<EditBox> box;

    public EditBoxOptional(AbstractContainerScreen<?> parent, Optional<EditBox> box) {
        this.parent = parent;
        this.box = box;
    }

    public boolean isPresent() {
        return box.isPresent();
    }

    public EditBox orElse(Supplier<EditBox> supplier) {
        return box.orElseGet(supplier);
    }

    @Nullable
    public EditBox orNull() {
        return box.orElse(null);
    }

    public EditBox unwrap() {
        return box.orElseThrow();
    }

    public EditBoxOptional setResponder(Consumer<String> responder) {
        box.ifPresent(b -> b.setResponder(responder));
        return this;
    }

    public EditBoxOptional focused() {
        box.ifPresent(parent::setFocused);
        return this;
    }

    public boolean handleMouseClicked(double mouseX, double mouseY, int button) {
        return box.map(eb -> eb.mouseClicked(mouseX, mouseY, button)).orElse(false);
    }

    public boolean handleCharTyped(char codePoint, int modifiers) {
        return box.map(eb -> eb.charTyped(codePoint, modifiers)).orElse(false);
    }

    public boolean handleKeyPressed(int keyCode, int scanCode, int modifiers) {
        return box.map(eb -> eb.keyPressed(keyCode, scanCode, modifiers)).orElse(false);
    }

    public void handleTick() {
        box.ifPresent(EditBox::tick);
    }

    public Optional<String> getValue() {
        return box.map(EditBox::getValue).filter(s -> !s.isEmpty());
    }

    public void setEditable(boolean editable) {
        box.ifPresent(b -> b.setEditable(editable));
    }
}
