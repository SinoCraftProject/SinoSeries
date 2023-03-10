package games.moegirl.sinocraft.sinocore.old.api.woodwork;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public class ModSignBlockEntity extends BlockEntity implements IWoodworkEntity {
    private static final String[] RAW_TEXT_FIELD_NAMES = new String[]{"Text1", "Text2", "Text3", "Text4"};
    private static final String[] FILTERED_TEXT_FIELD_NAMES = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
    private final Component[] messages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
    private final Component[] filteredMessages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
    @Nullable
    private UUID playerWhoMayEdit;
    @Nullable
    private FormattedCharSequence[] renderMessages;
    private boolean renderMessagedFiltered;
    private DyeColor color = DyeColor.BLACK;
    private boolean hasGlowingText;

    public ModSignBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    private Component loadLine(String textJson) {
        Component component = this.deserializeTextSafe(textJson);
        if (this.level instanceof ServerLevel) {
            try {
                return ComponentUtils.updateForEntity(this.createCommandSourceStack(null), component, null, 0);
            } catch (CommandSyntaxException ignored) {
            }
        }

        return component;
    }

    private Component deserializeTextSafe(String textJson) {
        try {
            Component component = Component.Serializer.fromJson(textJson);
            if (component != null) {
                return component;
            }
        } catch (Exception ignored) {
        }

        return TextComponent.EMPTY;
    }

    public Component getMessage(int line, boolean isFiltered) {
        return this.getMessages(isFiltered)[line];
    }

    public void setMessage(int line, Component message) {
        this.setMessage(line, message, message);
    }

    public void setMessage(int line, Component message, Component filteredMessage) {
        this.messages[line] = message;
        this.filteredMessages[line] = filteredMessage;
        this.renderMessages = null;
    }

    public FormattedCharSequence[] getRenderMessages(boolean isFiltered, Function<Component, FormattedCharSequence> formatted) {
        if (this.renderMessages == null || this.renderMessagedFiltered != isFiltered) {
            this.renderMessagedFiltered = isFiltered;
            this.renderMessages = new FormattedCharSequence[4];

            for (int i = 0; i < 4; ++i) {
                this.renderMessages[i] = formatted.apply(this.getMessage(i, isFiltered));
            }
        }

        return this.renderMessages;
    }

    private Component[] getMessages(boolean isFiltered) {
        return isFiltered ? this.filteredMessages : this.messages;
    }

    public void setPlayerWhoMayEdit(@Nullable UUID playerWhoMayEdit) {
        this.playerWhoMayEdit = playerWhoMayEdit;
    }

    public boolean canPlayerEdit(@Nullable Player entity) {
        return entity != null && (playerWhoMayEdit == null || entity.getUUID().equals(playerWhoMayEdit));
    }

    public boolean executeClickCommands(ServerPlayer player) {
        for (Component component : this.getMessages(player.isTextFilteringEnabled())) {
            Style style = component.getStyle();
            ClickEvent clickevent = style.getClickEvent();
            if (player.getServer() != null && clickevent != null && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                player.getServer().getCommands().performCommand(this.createCommandSourceStack(player), clickevent.getValue());
            }
        }
        return true;
    }

    public CommandSourceStack createCommandSourceStack(@Nullable ServerPlayer player) {
        String s = player == null ? "Sign" : player.getName().getString();
        Component component = player == null ? new TextComponent("Sign") : player.getDisplayName();
        Objects.requireNonNull(level);
        return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(this.worldPosition), Vec2.ZERO,
                (ServerLevel) level, 2, s, component, Objects.requireNonNull(level.getServer()), player);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public boolean setColor(DyeColor color) {
        if (color != this.getColor()) {
            this.color = color;
            this.markUpdated();
            return true;
        } else {
            return false;
        }
    }

    public boolean hasGlowingText() {
        return this.hasGlowingText;
    }

    public boolean setHasGlowingText(boolean hasGlowingText) {
        if (this.hasGlowingText != hasGlowingText) {
            this.hasGlowingText = hasGlowingText;
            this.markUpdated();
            return true;
        } else {
            return false;
        }
    }

    private void markUpdated() {
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        for (int i = 0; i < 4; ++i) {
            Component component = this.messages[i];
            String s = Component.Serializer.toJson(component);
            pTag.putString(RAW_TEXT_FIELD_NAMES[i], s);
            Component component1 = this.filteredMessages[i];
            if (!component1.equals(component)) {
                pTag.putString(FILTERED_TEXT_FIELD_NAMES[i], Component.Serializer.toJson(component1));
            }
        }

        pTag.putString("Color", this.color.getName());
        pTag.putBoolean("GlowingText", this.hasGlowingText);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.color = DyeColor.byName(pTag.getString("Color"), DyeColor.BLACK);

        for (int i = 0; i < 4; ++i) {
            String s = pTag.getString(RAW_TEXT_FIELD_NAMES[i]);
            Component component = this.loadLine(s);
            this.messages[i] = component;
            String s1 = FILTERED_TEXT_FIELD_NAMES[i];
            if (pTag.contains(s1, 8)) {
                this.filteredMessages[i] = this.loadLine(pTag.getString(s1));
            } else {
                this.filteredMessages[i] = component;
            }
        }

        this.renderMessages = null;
        this.hasGlowingText = pTag.getBoolean("GlowingText");
    }
}