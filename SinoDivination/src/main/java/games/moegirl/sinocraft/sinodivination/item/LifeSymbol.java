package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinocore.utility.OptionalTag;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinofoundation.block.entity.SophoraChestEntity;
import games.moegirl.sinocraft.sinofoundation.block.entity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
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

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 命符
 */
public class LifeSymbol extends Item {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public LifeSymbol() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity be = context.getLevel().getBlockEntity(context.getClickedPos());
        ItemStack stack = context.getItemInHand();
        if (be instanceof ICotinusEntity entity) {
            // 记录玩家 id
            getRecordEntityId(stack)
                    .filter(data -> entity.owner().isOwner(context.getPlayer()))
                    .ifPresent(uuid -> entity.owner().allow(uuid));
        } else if (be instanceof SophoraChestEntity chest) {
            // 记录玩家生辰八字
            chest.getEntity().ifPresent(r -> setRecordEntity(stack, r.uuid(), r.name(), r.birthday()));
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity placer, InteractionHand hand) {
        setRecordEntity(itemStack, placer.getUUID(), placer.getDisplayName(), null);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltips, flag);
        // 记录玩家名
        getRecordEntityName(stack)
                .map(c -> Component.translatable(SDLangKeys.SYMBOL_NAME).append(c))
                .ifPresent(tooltips::add);
        // 记录生辰八字
        getRecordEntityDate(stack)
                .map(d -> Component.translatable(SDLangKeys.SYMBOL_DATE).append(DATE_FORMATTER.format(d)))
                .ifPresent(tooltips::add);
    }

    /**
     * 获取命符记录的玩家
     *
     * @param stack 命符物品
     * @return 玩家 id
     */
    public static Optional<UUID> getRecordEntityId(ItemStack stack) {
        return OptionalTag.of(stack)
                .getCompound(SinoDivination.MODID + ".symbol")
                .getUUID("entity");
    }

    /**
     * 获取命符记录玩家名
     *
     * @param stack 命符物品
     * @return 玩家名
     */
    public static Optional<? extends Component> getRecordEntityName(ItemStack stack) {
        return OptionalTag.of(stack)
                .getCompound(SinoDivination.MODID + ".symbol")
                .getString("name")
                .map(Component.Serializer::fromJson)
                .or(() -> getRecordEntityId(stack)
                        .map(UUID::toString)
                        .map(Component::literal));
    }

    /**
     * 获取命符记录玩家生辰八字
     *
     * @param stack 命符物品
     * @return 玩家生辰八字
     */
    public static Optional<Instant> getRecordEntityDate(ItemStack stack) {
        return OptionalTag.of(stack)
                .get(SinoDivination.MODID + ".symbol")
                .mapToObj(ExtraCodecs.INSTANT_ISO8601);
    }

    /**
     * 记录玩家信息
     *
     * @param stack    命符物品
     * @param entity   实体 id
     * @param name     玩家名
     * @param birthday 生辰八字
     */
    public static void setRecordEntity(ItemStack stack, UUID entity, @Nullable Component name, @Nullable Instant birthday) {
        OptionalTag.ofOrCreate(stack).computeIfAbsent(SinoDivination.MODID + ".symbol", Tag.TAG_COMPOUND, CompoundTag::new)
                .put("entity", entity)
                .put("name", name, ExtraCodecs.COMPONENT)
                .put("birthday", birthday, ExtraCodecs.INSTANT_ISO8601);
    }
}
