package games.moegirl.sinocraft.sinodivination.old.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import games.moegirl.sinocraft.sinodivination.old.blockentity.ICotinusEntity;
import games.moegirl.sinocraft.sinodivination.old.blockentity.SophoraEntity;
import games.moegirl.sinocraft.sinodivination.old.item.LifeSymbol;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import games.moegirl.sinocraft.sinodivination.old.tree.SDTrees;
import games.moegirl.sinocraft.sinodivination.old.util.OwnerChecker;
import net.minecraft.commands.CommandSourceStack;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings("DuplicatedCode")
public class SDCommands {

    private static final Predicate<BlockState> P_COTINUS_DOOR = s -> s.is(SDTrees.COTINUS.<Block>getBlock(TreeBlockType.DOOR)) && s.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
    private static final Predicate<BlockState> P_COTINUS_TRAPDOOR = s -> s.is(SDTrees.COTINUS.<Block>getBlock(TreeBlockType.TRAPDOOR));
    private static final Predicate<BlockState> P_COTINUS_FENCEGATE = s -> s.is(SDTrees.COTINUS.<Block>getBlock(TreeBlockType.FENCE_GATE));
    private static final Predicate<BlockState> P_COTINUS_ANY = P_COTINUS_DOOR.or(P_COTINUS_TRAPDOOR).or(P_COTINUS_FENCEGATE);
    private static final Predicate<BlockState> P_SOPHORA_DOOR = s -> s.is(SDTrees.SOPHORA.<Block>getBlock(TreeBlockType.DOOR)) && s.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
    private static final Predicate<BlockState> P_SOPHORA_TRAPDOOR = s -> s.is(SDTrees.SOPHORA.<Block>getBlock(TreeBlockType.TRAPDOOR));
    private static final Predicate<BlockState> P_SOPHORA_FENCEGATE = s -> s.is(SDTrees.SOPHORA.<Block>getBlock(TreeBlockType.FENCE_GATE));
    private static final Predicate<BlockState> P_SOPHORA_ANY = P_SOPHORA_DOOR.or(P_SOPHORA_TRAPDOOR).or(P_SOPHORA_FENCEGATE);

    public static final int SEARCH_RANGE = 10;

    public static final String DEBUG = "debug";
    public static final String COTINUS = "cotinus";
    public static final String SOPHORA = "sophora";
    public static final String LIFESYMBOL = "lifesymbol";

    static {
//        REGISTER.register("sinodivination", rootBuilder -> rootBuilder
//                .then(DEBUG)
//                .requires(__ -> SinoCoreAPI.DEBUG)
//                // SinoDivination debug Cotinus randomOwner *
//                .then(COTINUS)
//                .then("random_owner")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getCotinusOwner(context).setOwner(UUID.randomUUID()))
//                // SinoDivination debug Cotinus setOwner *
//                .forward(COTINUS)
//                .then("set_owner")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getCotinusOwner(context).setOwner(context.getSource().getPlayerOrException()))
//                // SinoDivination debug Cotinus removeOwner *
//                .forward(COTINUS)
//                .then("remove_owner")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getCotinusOwner(context).setOwner((UUID) null))
//                // SinoDivination debug Cotinus addRecord *
//                .forward(COTINUS)
//                .then("add_record")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getCotinusOwner(context).allow(context.getSource().getPlayerOrException()))
//                // SinoDivination debug Cotinus removeRecord [x, y, z]
//                .forward(COTINUS)
//                .then("remove_record")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getCotinusOwner(context).removeAllow(context.getSource().getPlayerOrException()))
//                // SinoDivination debug Sophora randomOwner [x, y, z]
//                .forward(DEBUG)
//                .then(SOPHORA)
//                .then("random_owner")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getSophoraDoor(context).setEntity(UUID.randomUUID()))
//                // SinoDivination debug Sophora setOwner [x, y, z]
//                .forward(SOPHORA)
//                .then("set_owner")
//                .then("type", StringArgumentType.string())
//                .suggests((context, builder) -> builder.suggest("any").suggest("door").suggest("trapdoor").suggest("fencegate").buildFuture())
//                .execute(context -> getSophoraDoor(context).setEntity(context.getSource().getPlayerOrException()))
//                // SinoDivination debug Lifesymbol random
//                .forward(DEBUG)
//                .then(LIFESYMBOL)
//                .then("random_record")
//                .execute(SDCommands::randomSymbolRecord)
//        );
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

    private static void randomSymbolRecord(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
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
        RandomSource random = player.getRandom();
        LifeSymbol.setRecordEntity(stack, UUID.randomUUID(), null, LocalDateTime.of(
                LocalDate.ofEpochDay(Math.abs(random.nextLong())),
                LocalTime.ofNanoOfDay(Math.abs(random.nextLong()))));
    }
}
