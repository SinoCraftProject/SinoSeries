package games.moegirl.sinocraft.sinodivination.old.item;

import games.moegirl.sinocraft.sinocore.utility.OptionalTag;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.blockentity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SophoraChestEntity;
import games.moegirl.sinocraft.sinodivination.old.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.old.util.TagSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LifeSymbol extends Item {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public LifeSymbol() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockEntity be = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
        ItemStack stack = pContext.getItemInHand();
        if (be instanceof ICotinusEntity entity) {
            getRecordEntityId(stack)
                    .filter(data -> entity.owner().isOwner(pContext.getPlayer()))
                    .ifPresent(uuid -> entity.owner().allow(uuid));
        } else if (be instanceof SophoraChestEntity chest) {
            chest.getEntity().ifPresent(r -> setRecordEntity(stack, r.uuid(), r.name(), r.birthday()));
        }
        return super.useOn(pContext);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        setRecordEntity(pStack, pInteractionTarget.getUUID(), pInteractionTarget.getDisplayName(), null);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        getRecordEntityName(pStack)
                .map(c -> Component.translatable(SDLangKeys.SYMBOL_NAME).append(c))
                .ifPresent(pTooltipComponents::add);
        getRecordEntityDate(pStack)
                .map(d -> Component.translatable(SDLangKeys.SYMBOL_DATE).append(d.toLocalDate().format(DATE_FORMATTER)))
                .ifPresent(pTooltipComponents::add);
    }

    public static Optional<UUID> getRecordEntityId(ItemStack stack) {
        return OptionalTag.of(stack)
                .getCompound(SinoDivination.MODID + ".symbol")
                .getUUID("entity");
    }

    public static Optional<? extends Component> getRecordEntityName(ItemStack stack) {
        return OptionalTag.of(stack)
                .getCompound(SinoDivination.MODID + ".symbol")
                .getString("name")
                .map(Component.Serializer::fromJson)
                .or(() -> getRecordEntityId(stack)
                        .map(UUID::toString)
                        .map(Component::literal));
    }

    public static Optional<LocalDateTime> getRecordEntityDate(ItemStack stack) {
        return OptionalTag.of(stack)
                .getCompound(SinoDivination.MODID + ".symbol")
                .mapToObj(TagSerializers::readDate);
    }

    public static void setRecordEntity(ItemStack stack, UUID entity, @Nullable Component name, @Nullable LocalDateTime birthday) {
        OptionalTag.ofOrCreate(stack).computeIfAbsent(SinoDivination.MODID + ".symbol", Tag.TAG_COMPOUND, CompoundTag::new)
                .put("entity", entity)
                .put("name", name, TagSerializers::componentSerializer)
                .put(birthday, TagSerializers::writeDate);
    }
}
