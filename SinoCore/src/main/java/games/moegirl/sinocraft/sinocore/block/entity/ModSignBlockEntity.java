package games.moegirl.sinocraft.sinocore.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import games.moegirl.sinocraft.sinocore.woodwork.IWoodworkEntity;
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

public class ModSignBlockEntity extends BlockEntity implements IWoodworkEntity<ModSignBlockEntity> {
    private static final int MAX_TEXT_LINE_WIDTH = 90;
    private static final int TEXT_LINE_HEIGHT = 10;
    private static final String[] RAW_TEXT_FIELD_NAMES = new String[]{"Text1", "Text2", "Text3", "Text4"};
    private static final String[] FILTERED_TEXT_FIELD_NAMES = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
    private final Component[] messages = new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};
    private final Component[] filteredMessages = new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};
    @Nullable
    private UUID playerWhoMayEdit;
    @Nullable
    private FormattedCharSequence[] renderMessages;
    private boolean renderMessagedFiltered;
    private DyeColor color = DyeColor.BLACK;
    private boolean hasGlowingText;

    public ModSignBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
        super(type, blockPos, blockState);
    }

    public int getTextLineHeight() {
        return TEXT_LINE_HEIGHT;
    }

    public int getMaxTextLineWidth() {
        return MAX_TEXT_LINE_WIDTH;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        for (int i = 0; i < 4; ++i) {
            Component component = messages[i];
            String s = Component.Serializer.toJson(component);
            pTag.putString(RAW_TEXT_FIELD_NAMES[i], s);
            Component component1 = filteredMessages[i];
            if (component1.equals(component)) continue;
            pTag.putString(FILTERED_TEXT_FIELD_NAMES[i], Component.Serializer.toJson(component1));
        }

        pTag.putString("Color", color.getName());
        pTag.putBoolean("GlowingText", hasGlowingText);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        color = DyeColor.byName(pTag.getString("Color"), DyeColor.BLACK);
        for (int i = 0; i < 4; ++i) {
            String s = pTag.getString(RAW_TEXT_FIELD_NAMES[i]);
            Component component = loadLine(s);
            messages[i] = component;
            String s1 = FILTERED_TEXT_FIELD_NAMES[i];
            filteredMessages[i] = pTag.contains(s1, 8) ? loadLine(pTag.getString(s1)) : component;
        }

        renderMessages = null;
        hasGlowingText = pTag.getBoolean("GlowingText");
    }

    private Component loadLine(String textJson) {
        Component component = deserializeTextSafe(textJson);
        if (level instanceof ServerLevel) {
            try {
                return ComponentUtils.updateForEntity(createCommandSourceStack(null), component, null, 0);
            } catch (CommandSyntaxException ignored) {
            }
        }
        return component;
    }

    private Component deserializeTextSafe(String textJson) {
        try {
            Component component = Component.Serializer.fromJson(textJson);
            if (component != null) return component;
        } catch (Exception ignored) {
        }

        return CommonComponents.EMPTY;
    }

    public Component getMessage(int line, boolean isFiltered) {
        return getMessages(isFiltered)[line];
    }

    public void setMessage(int line, Component message) {
        setMessage(line, message, message);
    }

    public void setMessage(int line, Component message, Component filteredMessage) {
        messages[line] = message;
        filteredMessages[line] = filteredMessage;
        renderMessages = null;
    }

    public FormattedCharSequence[] getRenderMessages(boolean isFiltered, Function<Component, FormattedCharSequence> formatted) {
        if (renderMessages == null || renderMessagedFiltered != isFiltered) {
            renderMessagedFiltered = isFiltered;
            renderMessages = new FormattedCharSequence[4];
            for (int i = 0; i < 4; ++i) {
                renderMessages[i] = formatted.apply(getMessage(i, isFiltered));
            }
        }

        return renderMessages;
    }

    private Component[] getMessages(boolean isFiltered) {
        return isFiltered ? filteredMessages : messages;
    }

    public boolean canPlayerEdit(@Nullable Player entity) {
        return entity != null && (playerWhoMayEdit == null || entity.getUUID().equals(playerWhoMayEdit));
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public void setPlayerWhoMayEdit(@Nullable UUID playerWhoMayEdit) {
        this.playerWhoMayEdit = playerWhoMayEdit;
    }

    public boolean hasAnyClickCommands(Player player) {
        for (Component component : getMessages(player.isTextFilteringEnabled())) {
            Style style = component.getStyle();
            ClickEvent clickEvent = style.getClickEvent();
            if (clickEvent == null || clickEvent.getAction() != ClickEvent.Action.RUN_COMMAND) continue;
            return true;
        }
        return false;
    }

    public boolean executeClickCommands(ServerPlayer player) {
        for (Component component : getMessages(player.isTextFilteringEnabled())) {
            Style style = component.getStyle();
            ClickEvent clickevent = style.getClickEvent();
            if (player.getServer() != null && clickevent != null && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                player.getServer().getCommands().performPrefixedCommand(createCommandSourceStack(player), clickevent.getValue());
            }
        }
        return true;
    }

    public CommandSourceStack createCommandSourceStack(@Nullable ServerPlayer player) {
        String s = player == null ? "Sign" : player.getName().getString();
        Component component = player == null ? Component.literal("Sign") : player.getDisplayName();
        Objects.requireNonNull(level);
        return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(worldPosition), Vec2.ZERO,
                (ServerLevel) level, 2, s, component, Objects.requireNonNull(level.getServer()), player);
    }

    public DyeColor getColor() {
        return color;
    }

    public boolean setColor(DyeColor color) {
        if (this.color != color) {
            this.color = color;
            markUpdated();
            return true;
        } else {
            return false;
        }
    }

    public boolean hasGlowingText() {
        return hasGlowingText;
    }

    public boolean setHasGlowingText(boolean hasGlowingText) {
        if (this.hasGlowingText != hasGlowingText) {
            this.hasGlowingText = hasGlowingText;
            markUpdated();
            return true;
        } else {
            return false;
        }
    }

    private void markUpdated() {
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }
}