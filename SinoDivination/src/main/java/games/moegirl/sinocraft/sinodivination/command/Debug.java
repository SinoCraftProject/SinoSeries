package games.moegirl.sinocraft.sinodivination.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinodivination.SDTrees;
import games.moegirl.sinocraft.sinodivination.blockentity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.blockentity.SophoraEntity;
import games.moegirl.sinocraft.sinodivination.item.LifeSymbol;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinofoundation.capability.SFDCapabilities;
import games.moegirl.sinocraft.sinofoundation.capability.entity.birthday.Birthday;
import games.moegirl.sinocraft.sinofoundation.utility.OwnerChecker;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Predicate;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * @author luqin2007
 */
class Debug {

    private static final Predicate<BlockState> P_COTINUS_DOOR = s -> s.is(SDTrees.COTINUS.<Block>getBlock(TreeBlockType.DOOR)) && s.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
    private static final Predicate<BlockState> P_COTINUS_TRAPDOOR = s -> s.is(SDTrees.COTINUS.<Block>getBlock(TreeBlockType.TRAPDOOR));
    private static final Predicate<BlockState> P_COTINUS_FENCEGATE = s -> s.is(SDTrees.COTINUS.<Block>getBlock(TreeBlockType.FENCE_GATE));
    private static final Predicate<BlockState> P_COTINUS_ANY = P_COTINUS_DOOR.or(P_COTINUS_TRAPDOOR).or(P_COTINUS_FENCEGATE);
    private static final Predicate<BlockState> P_SOPHORA_DOOR = s -> s.is(SDTrees.SOPHORA.<Block>getBlock(TreeBlockType.DOOR)) && s.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
    private static final Predicate<BlockState> P_SOPHORA_TRAPDOOR = s -> s.is(SDTrees.SOPHORA.<Block>getBlock(TreeBlockType.TRAPDOOR));
    private static final Predicate<BlockState> P_SOPHORA_FENCEGATE = s -> s.is(SDTrees.SOPHORA.<Block>getBlock(TreeBlockType.FENCE_GATE));
    private static final Predicate<BlockState> P_SOPHORA_ANY = P_SOPHORA_DOOR.or(P_SOPHORA_TRAPDOOR).or(P_SOPHORA_FENCEGATE);
    public static final int SEARCH_RANGE = 10;

    public static ArgumentBuilder<CommandSourceStack, ?> command() {
        return literal("debug").requires(source -> SinoCore.DEBUG)
                .then(literal("cotinus")
                        .then(literal("set_owner")
                                .then(blockTypeArgument().executes(Debug::cotinusRandomOwner)
                                        .then(argument("owner", EntityArgument.player()).executes(Debug::cotinusOwner))))
                        .then(literal("clear_owner")
                                .then(blockTypeArgument().executes(Debug::cotinusClearOwner)))
                        .then(literal("allow")
                                .then(blockTypeArgument().executes(Debug::cotinusAllow)
                                        .then(argument("player", EntityArgument.player()).executes(Debug::cotinusAllowPlayer))))
                        .then(literal("remove_allow")
                                .then(blockTypeArgument().executes(Debug::cotinusRemoveAllow)
                                        .then(argument("player", EntityArgument.player()).executes(Debug::cotinusRemoveAllowPlayer))))
                        .then(literal("sophora")
                                .then(literal("set_owner")
                                        .then(blockTypeArgument().executes(Debug::sophoraRandomOwner)
                                                .then(argument("owner", EntityArgument.player()).executes(Debug::sophoraOwner)))))
                        .then(literal("lifesymbol")
                                .then(literal("record").executes(Debug::symbolRecordRandom)
                                        .then(argument("player", EntityArgument.player()).executes(Debug::symbolRecordPlayer)))));
    }

    private static RequiredArgumentBuilder<CommandSourceStack, String> blockTypeArgument() {
        return argument("type", StringArgumentType.string()).suggests((context, builder) -> builder
                .suggest("any")
                .suggest("door")
                .suggest("trapdoor")
                .suggest("fencegate")
                .buildFuture());
    }

    private static OwnerChecker getCotinusOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel level = context.getSource().getLevel();
        String type = StringArgumentType.getString(context, "type");
        Predicate<BlockState> predicate = switch (type) {
            case "door" -> P_COTINUS_DOOR;
            case "trapdoor" -> P_COTINUS_TRAPDOOR;
            case "fencegate" -> P_COTINUS_FENCEGATE;
            case "any" -> P_COTINUS_ANY;
            default -> {
                Message message = new LiteralMessage("Type " + type + " is error");
                throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
            }
        };
        Vec3 position = context.getSource().getPosition();
        int hr = SEARCH_RANGE / 2;
        int y0 = (int) (position.y - hr);
        int z0 = (int) (position.z - hr);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.x - hr, y0, z0);
        for (int x = 0; x < SEARCH_RANGE; x++) {
            pos.setY(y0);
            for (int y = 0; y < SEARCH_RANGE; y++) {
                pos.setZ(z0);
                for (int z = 0; z < SEARCH_RANGE; z++) {
                    BlockPos.MutableBlockPos p = pos.move(0, 0, 1);
                    if (level.isAreaLoaded(p, 1) && predicate.test(level.getBlockState(p)) && level.getBlockEntity(p) instanceof ICotinusEntity ce) {
                        return ce.owner();
                    }
                }
                pos.move(0, 1, 0);
            }
            pos.move(1, 0, 0);
        }
        LiteralMessage message = new LiteralMessage("Not found cotinus entity around.");
        CommandExceptionType e = new SimpleCommandExceptionType(message);
        throw new CommandSyntaxException(e, message);
    }

    private static SophoraEntity getSophoraDoor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel level = context.getSource().getLevel();
        String type = StringArgumentType.getString(context, "type");
        Predicate<BlockState> predicate = switch (type) {
            case "door" -> P_SOPHORA_DOOR;
            case "trapdoor" -> P_SOPHORA_TRAPDOOR;
            case "fencegate" -> P_SOPHORA_FENCEGATE;
            case "any" -> P_SOPHORA_ANY;
            default -> {
                Message message = new LiteralMessage("Type " + type + " is error");
                throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
            }
        };
        Vec3 position = context.getSource().getPosition();
        int hr = SEARCH_RANGE / 2;
        int y0 = (int) (position.y - hr);
        int z0 = (int) (position.z - hr);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.x - hr, y0, z0);
        for (int x = 0; x < SEARCH_RANGE; x++) {
            pos.setY(y0);
            for (int y = 0; y < SEARCH_RANGE; y++) {
                pos.setZ(z0);
                for (int z = 0; z < SEARCH_RANGE; z++) {
                    BlockPos.MutableBlockPos p = pos.move(0, 0, 1);
                    if (level.isAreaLoaded(p, 1) && predicate.test(level.getBlockState(p)) && level.getBlockEntity(p) instanceof SophoraEntity se) {
                        return se;
                    }
                }
                pos.move(0, 1, 0);
            }
            pos.move(1, 0, 0);
        }
        LiteralMessage message = new LiteralMessage("Not found sophora entity around.");
        CommandExceptionType e = new SimpleCommandExceptionType(message);
        throw new CommandSyntaxException(e, message);
    }

    private static ItemStack getLifeSymbol(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ItemStack stack = player.getMainHandItem();
        if (!stack.is(SDItems.LIFE_SYMBOL.get())) {
            stack = player.getOffhandItem();
            if (!stack.is(SDItems.LIFE_SYMBOL.get())) {
                LiteralMessage message = new LiteralMessage("Please hold a life symbol.");
                CommandExceptionType e = new SimpleCommandExceptionType(message);
                throw new CommandSyntaxException(e, message);
            }
        }
        return stack;
    }

    private static int cotinusRandomOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).setOwner(UUID.randomUUID());
        return SINGLE_SUCCESS;
    }

    private static int cotinusOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).setOwner(EntityArgument.getPlayer(context, "owner"));
        return SINGLE_SUCCESS;
    }

    private static int cotinusClearOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).setOwner((UUID) null);
        return SINGLE_SUCCESS;
    }

    private static int cotinusAllow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).allow(context.getSource().getPlayerOrException().getUUID());
        return SINGLE_SUCCESS;
    }

    private static int cotinusAllowPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).allow(EntityArgument.getPlayer(context, "player").getUUID());
        return SINGLE_SUCCESS;
    }

    private static int cotinusRemoveAllow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).removeAllow(context.getSource().getPlayerOrException().getUUID());
        return SINGLE_SUCCESS;
    }

    private static int cotinusRemoveAllowPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getCotinusOwner(context).removeAllow(EntityArgument.getPlayer(context, "player").getUUID());
        return SINGLE_SUCCESS;
    }

    private static int sophoraRandomOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getSophoraDoor(context).setEntity(UUID.randomUUID());
        return SINGLE_SUCCESS;
    }

    private static int sophoraOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        getSophoraDoor(context).setEntity(EntityArgument.getPlayer(context, "owner"));
        return SINGLE_SUCCESS;
    }

    private static int symbolRecordRandom(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        RandomSource random = context.getSource().getLevel().random;
        Instant birthday = Instant.ofEpochMilli(Math.abs(random.nextLong()));
        LifeSymbol.setRecordEntity(getLifeSymbol(context), UUID.randomUUID(), null, birthday);
        return SINGLE_SUCCESS;
    }

    private static int symbolRecordPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        Instant birthday = player.getCapability(SFDCapabilities.BIRTHDAY).map(Birthday::getBirthday).orElseThrow();
        LifeSymbol.setRecordEntity(getLifeSymbol(context), player.getUUID(), player.getDisplayName(), birthday);
        return SINGLE_SUCCESS;
    }
}