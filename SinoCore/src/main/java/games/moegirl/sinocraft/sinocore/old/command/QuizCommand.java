package games.moegirl.sinocraft.sinocore.old.command;

import cn.hutool.core.thread.ThreadUtil;
import com.google.gson.Gson;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.api.capability.IQuizzingPlayer;
import games.moegirl.sinocraft.sinocore.api.capability.SCCapabilities;
import games.moegirl.sinocraft.sinocore.capability.QuizzingPlayer;
import games.moegirl.sinocraft.sinocore.config.QuizModelConfig;
import games.moegirl.sinocraft.sinocore.config.model.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class QuizCommand {
    public static final Gson GSON = new Gson();

    public static LiteralCommandNode<CommandSourceStack> QUIZ = literal("quiz")
            .then(literal("start")
                    .requires(s -> s.hasPermission(2))
                    .then(argument("max_stage", IntegerArgumentType.integer(1, 50))
                            .then(argument("player", EntityArgument.player())
                                    .executes(QuizCommand::onStart))
                    ))
            .then(literal("next")
                    .then(argument("answer", StringArgumentType.string())
                            .then(argument("player", EntityArgument.player())
                                    .executes(QuizCommand::onNext)
                            )
                    )
            )
            .then(literal("giveup")
                    .then(argument("player", EntityArgument.player())
                            .executes(QuizCommand::onGiveUp)
                    )
            )
            .then(literal("reload")
                    .requires(s -> s.hasPermission(2))
                    .executes(QuizCommand::onReload)
            )
            .then(literal("load")
                    .requires(s -> s.hasPermission(2))
                    .then(argument("url", StringArgumentType.greedyString())
                            .executes(QuizCommand::onLoad)
                    )
            )
            .then(literal("rank")
                    .executes(stack -> onRank(stack, false, false))
                    .then(argument("count", IntegerArgumentType.integer(1, 10))
                            .executes(stack -> onRank(stack, true, false))
                    )
                    .then(literal("player")
                            .then(argument("player", EntityArgument.player())
                                    .executes(QuizCommand::onRankPlayer))
                    )
            )
            .build();

    public static int onRankPlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var selector = context.getArgument("player", EntitySelector.class);
        var player = selector.findSinglePlayer(context.getSource());

        context.getSource().sendSuccess(new TranslatableComponent(MESSAGE_FETCHING)
                .withStyle(ChatFormatting.AQUA), false);
        try {
            var model = doFetchRank(player.getGameProfile().getName());

            context.getSource().sendSuccess(new TranslatableComponent(MESSAGE_RANK_MY_BEST,
                    model.best).withStyle(ChatFormatting.GREEN), false);
        } catch (Exception ex) {
            context.getSource().sendFailure(new TranslatableComponent(MESSAGE_RANK_FETCH_FAILED)
                    .withStyle(ChatFormatting.AQUA));

            SinoCore.getLogger().warn("Fetch rank list failed, did the URL is correct?");
            ex.printStackTrace();

            return 0;
        }

        return 1;
    }

    public static int onRank(CommandContext<CommandSourceStack> context, boolean withCount, boolean withBest) {
        if (!QuizModelConfig.CONFIG.RANK_ENABLED.get()) {
            makeNotEnabled(context.getSource());
        }

        var source = context.getSource();

        context.getSource().sendSuccess(new TranslatableComponent(MESSAGE_FETCHING)
                .withStyle(ChatFormatting.AQUA), false);

        try {
            var model = doFetchRank(withBest);

            var msg = new TranslatableComponent(MESSAGE_RANK_TITLE).withStyle(ChatFormatting.AQUA).append("\n");
            var count = 5;
            if (withCount) {
                count = context.getArgument("count", Integer.class);
            }

            if (count > model.ranks.length) {
                count = model.ranks.length;
            }

            for (var i = 1; i <= count; i++) {
                var m = model.ranks[i - 1];
                msg.append(new TranslatableComponent(MESSAGE_RANK_BODY, m.rank, m.player.id,
                        m.timeUsed, m.tries)).withStyle(ChatFormatting.GREEN).append("\n");
            }
            msg.append(new TranslatableComponent(MESSAGE_RANK_FOOTER).withStyle(ChatFormatting.AQUA));

            source.sendSuccess(msg, false);

        } catch (Exception ex) {
            context.getSource().sendFailure(new TranslatableComponent(MESSAGE_RANK_FETCH_FAILED)
                    .withStyle(ChatFormatting.AQUA));

            SinoCore.getLogger().warn("Fetch rank list failed, did the URL is correct?");
            ex.printStackTrace();

            return 0;
        }

        return 1;
    }

    private static RankBoardModel.BestModel doFetchRank(String me) throws Exception {
        SinoCore.getLogger().info("Fetching rank list.");

        var dataUrl = QuizModelConfig.CONFIG.RANK_URL.get();
        if (me != null) {
            dataUrl += "?player=" + me + "&best=true";
        }

        AtomicReference<String> data = new AtomicReference<>();
        String finalDataUrl = dataUrl;
        ThreadUtil.execute(() -> {
            try {
                data.set(IOUtils.toString(new URL(finalDataUrl).toURI(), StandardCharsets.UTF_8));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        var model = GSON.fromJson(data.get(), RankBoardModel.BestModel.class);

        SinoCore.getLogger().info("Fetch best rank successfully!");
        return model;
    }

    private static RankBoardModel doFetchRank(boolean withBest) throws Exception {
        SinoCore.getLogger().info("Fetching rank list.");

        var dataUrl = QuizModelConfig.CONFIG.RANK_URL.get();
        if (withBest) {
            dataUrl += "?best=true";
        }

        AtomicReference<String> data = new AtomicReference<>();
        String finalDataUrl = dataUrl;
        ThreadUtil.execute(() -> {
            try {
                data.set(IOUtils.toString(new URL(finalDataUrl).toURI(), StandardCharsets.UTF_8));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        var model = GSON.fromJson(data.get(), RankBoardModel.class);

        SinoCore.getLogger().info("Fetch rank list successfully!");
        return model;
    }

    public static int onStart(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var selector = context.getArgument("player", EntitySelector.class);
        var player = selector.findSinglePlayer(context.getSource());

        if (!QuizModelConfig.CONFIG.ENABLED.get()) {
            makeNotEnabled(player);
            return 0;
        }

        var quiz = player.getCapability(SCCapabilities.QUIZZING_PLAYER_CAPABILITY).orElse(new QuizzingPlayer());
        var maxStage = context.getArgument("max_stage", Integer.class);

        doStart(player, quiz, maxStage);
        return Command.SINGLE_SUCCESS;
    }

    public static int onNext(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var selector = context.getArgument("player", EntitySelector.class);
        var player = selector.findSinglePlayer(context.getSource());

        var quiz = player.getCapability(SCCapabilities.QUIZZING_PLAYER_CAPABILITY).orElse(new QuizzingPlayer());
        var answer = context.getArgument("answer", String.class);

        doNext(player, quiz, answer);

        return Command.SINGLE_SUCCESS;
    }

    public static int onGiveUp(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var selector = context.getArgument("player", EntitySelector.class);
        var player = selector.findSinglePlayer(context.getSource());
        var quiz = player.getCapability(SCCapabilities.QUIZZING_PLAYER_CAPABILITY).orElse(new QuizzingPlayer());

        doFail(player, quiz);
        postRecord(player, quiz);

        return Command.SINGLE_SUCCESS;
    }

    public static int onReload(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(new TextComponent("Reloading...")
                .withStyle(ChatFormatting.DARK_AQUA), true);

        if (!QuizModelConfig.CONFIG.ENABLED.get()) {
            context.getSource().sendSuccess(new TextComponent("Not enabled feature."), true);
            return 0;
        }

        QuizModel.getInstance().reFetch(true);
        return 1;
    }

    public static int onLoad(CommandContext<CommandSourceStack> context) {
        var url = context.getArgument("url", String.class);

        context.getSource().sendSuccess(new TextComponent("Temporarily set Quiz URL to: " + url)
                        .withStyle(ChatFormatting.DARK_AQUA), true);

        QuizConstants.URL = url;

        return onReload(context);
    }

    public static QuizModel.Question nextQuestion() {
        return QuizModel.getInstance().random();
    }

    public static void doStart(Player player, IQuizzingPlayer quiz, int maxStage) {
        quiz.deserializeNBT(new QuizzingPlayer().serializeNBT());

        quiz.setStartTime(System.currentTimeMillis() / 1000L);

        quiz.setQuizzing(true);
        quiz.setSucceed(false);
        quiz.setMaxQuizStage(maxStage);
        quiz.setQuizStage(0);
        quiz.clearQuestionUsed();
        quiz.clearAnswers();

        makeStarted(player);

        doNext(player, quiz);
    }

    public static boolean isCorrect(Player player, IQuizzingPlayer quiz, String answer) {
        return quiz.isCorrect(answer);
    }

    public static void doNext(Player player, IQuizzingPlayer quiz) {
        quiz.clearAnswers();

        var question = nextQuestion();
        var var1 = 0;
        while (quiz.questionHasUsed(question.id)) {
            var1 += 1;
            question = nextQuestion();
            if (var1 >= 100) {
                throw new RuntimeException("Do not hit me.");
            }
        }

        quiz.setQuestion(question.id, question.question());

        var res = shuffle(player, question.answers());

        // Todo: qyl27: we have 4 answers for now.
        if (res.size() != 4) {
            throw new RuntimeException("No more answer!");
        }

        quiz.addAnswer(res.get(0).answer(), "A", res.get(0).isCorrect());
        quiz.addAnswer(res.get(1).answer(), "B", res.get(1).isCorrect());
        quiz.addAnswer(res.get(2).answer(), "C", res.get(2).isCorrect());
        quiz.addAnswer(res.get(3).answer(), "D", res.get(3).isCorrect());

        makeQuestion(player, quiz);
    }

    public static List<QuizModel.Question.Answer> shuffle(Player player, List<QuizModel.Question.Answer> answers) {
        var rand = player.getRandom();

        var length = answers.size();

        for (int i = 0; i < length; i++) {
            var randIndex = rand.nextInt(0, length);
            var temp = answers.get(i);
            answers.set(i, answers.get(randIndex));
            answers.set(randIndex, temp);
        }

        return answers;
    }

    public static boolean doNext(Player player, IQuizzingPlayer quiz, String answer) {
        if (!quiz.isQuizzing()) {
            makeWrongState(player);
            return false;
        }

        quiz.setQuizStage(quiz.getQuizStage() + 1);

        if (!isCorrect(player, quiz, answer)) {
            makeWrongAnswer(player);
            postRecord(player, quiz);
            doFail(player, quiz);
            return true;
        }

        makeCorrectAnswer(player);

        if (hasReachedMaxStage(player, quiz)) {
            postRecord(player, quiz);
            doSucceed(player, quiz);
        } else {
            doNext(player, quiz);
        }
        return true;
    }

    public static boolean hasReachedMaxStage(Player player, IQuizzingPlayer quiz) {
        return quiz.getQuizStage() >= quiz.maxQuizStage();
    }

    public static boolean isEnded(Player player, IQuizzingPlayer quiz) {
        return hasReachedMaxStage(player, quiz) || !quiz.isQuizzing();
    }

    public static boolean doFail(Player player, IQuizzingPlayer quiz) {
        if (!quiz.isQuizzing()) {
            makeWrongState(player);

            return false;
        }

        quiz.setSucceed(false);
        quiz.setQuizzing(false);

        makeFail(player);
        return true;
    }

    private static boolean postRecord(Player player, IQuizzingPlayer quiz) {
        var client = HttpClients.createDefault();
        var post = new HttpPost(QuizModelConfig.CONFIG.RANK_URL.get());

        var data = new PostRankModel();
        data.key = QuizModelConfig.CONFIG.RANK_KEY.get();
        var span = new NewRankRecordModel.TimeSpan();
        span.start = quiz.getStartTime();
        span.end = System.currentTimeMillis() / 1000L;
        data.time = span;
        data.successful = quiz.isSucceed();
        var pd = new RankBoardModel.PlayerData();
        pd.id = player.getGameProfile().getName();
        pd.uuid = player.getUUID();
        data.player = pd;

        var json = GSON.toJson(data);
        var entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);

        AtomicBoolean isBest = new AtomicBoolean(false);
        ThreadUtil.execute(() -> {
            try {
                CloseableHttpResponse response = client.execute(post);

                if (response.getStatusLine().getStatusCode() == 200) {
                    var resp = EntityUtils.toString(response.getEntity());
                    isBest.set(GSON.fromJson(resp, PostRankModel.Response.class).best);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return isBest.get();
    }

    public static boolean doSucceed(Player player, IQuizzingPlayer quiz) {
        if (!isEnded(player, quiz) && !quiz.isQuizzing()) {
            makeWrongState(player);
            return false;
        }

        if (!quiz.isQuizzing()) {
            makeWrongState(player);
            return false;
        }

        quiz.setSucceed(true);
        quiz.setQuizzing(false);

        makeSucceed(player);
        return true;
    }

    public static final String MESSAGE_NOT_PLAYER = "sinocore.command.quiz.not_player";
    public static final String MESSAGE_NOT_STARTED = "sinocore.command.quiz.not_started";
    public static final String MESSAGE_NOT_ENABLED = "sinocore.command.quiz.not_enabled";
    public static final String MESSAGE_SUCCEED = "sinocore.command.quiz.succeed";
    public static final String MESSAGE_BROADCAST_SUCCEED = "sinocore.command.quiz.broadcast_succeed";
    public static final String MESSAGE_FAIL = "sinocore.command.quiz.fail";
    public static final String MESSAGE_WRONG_STATE = "sinocore.command.quiz.wrong_state";
    public static final String MESSAGE_STARTED = "sinocore.command.quiz.started";
    public static final String MESSAGE_QUESTION = "sinocore.command.quiz.question";
    public static final String MESSAGE_QUESTION_LAST = "sinocore.command.quiz.question_last";
    public static final String MESSAGE_ANSWER_MARKED = "sinocore.command.quiz.answer_marked";
    public static final String MESSAGE_ANSWER_WRONG = "sinocore.command.quiz.answer_wrong";
    public static final String MESSAGE_ANSWER_RIGHT = "sinocore.command.quiz.answer_right";
    public static final String MESSAGE_ANSWER_A = "sinocore.command.quiz.answer_a";
    public static final String MESSAGE_ANSWER_B = "sinocore.command.quiz.answer_b";
    public static final String MESSAGE_ANSWER_C = "sinocore.command.quiz.answer_c";
    public static final String MESSAGE_ANSWER_D = "sinocore.command.quiz.answer_d";
    public static final String MESSAGE_RANK_FETCH_FAILED = "sinocore.command.quiz.rank_fetch_failed";
    public static final String MESSAGE_RANK_TITLE = "sinocore.command.quiz.rank_title";
    public static final String MESSAGE_RANK_FOOTER = "sinocore.command.quiz.rank_footer";
    public static final String MESSAGE_RANK_BODY = "sinocore.command.quiz.rank_body";
    public static final String MESSAGE_RANK_MY_BEST = "sinocore.command.quiz.rank_my_best";
    public static final String MESSAGE_FETCHING = "sinocore.command.quiz.rank_fetching";


    public static void makeNotPlayer(CommandSourceStack source) {
        source.sendFailure(new TranslatableComponent(MESSAGE_NOT_PLAYER).withStyle(ChatFormatting.RED));
    }

    public static void makeNotStarted(Player player) {
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_NOT_STARTED)
                .withStyle(ChatFormatting.RED), false);
    }

    public static void makeSucceed(Player player) {
        broadcast(player, new TranslatableComponent(MESSAGE_BROADCAST_SUCCEED, player.getDisplayName())
                .withStyle(ChatFormatting.GREEN)
                .withStyle(ChatFormatting.BOLD));
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_SUCCEED)
                .withStyle(ChatFormatting.GREEN)
                .withStyle(ChatFormatting.BOLD), false);
    }

    public static void makeFetchError(CommandSourceStack source) {
        source.sendFailure(new TranslatableComponent(MESSAGE_RANK_FETCH_FAILED).withStyle(ChatFormatting.RED));
    }

    public static void makeFail(Player player) {
        player.createCommandSourceStack().sendFailure(new TranslatableComponent(MESSAGE_FAIL)
                .withStyle(ChatFormatting.RED));
    }

    public static void makeWrongState(Player player) {
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_WRONG_STATE)
                .withStyle(ChatFormatting.RED), false);
    }

    public static void makeWrongAnswer(Player player) {
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_ANSWER_WRONG)
                .withStyle(ChatFormatting.RED), false);
    }

    public static void makeNotEnabled(Player player) {
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_NOT_ENABLED)
                .withStyle(ChatFormatting.RED), false);
    }

    public static void makeNotEnabled(CommandSourceStack stack) {
        stack.sendSuccess(new TranslatableComponent(MESSAGE_NOT_ENABLED)
                .withStyle(ChatFormatting.RED), false);
    }

    public static void makeCorrectAnswer(Player player) {
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_ANSWER_RIGHT)
                .withStyle(ChatFormatting.LIGHT_PURPLE), false);
    }

    public static void makeStarted(Player player) {
        player.createCommandSourceStack().sendSuccess(new TranslatableComponent(MESSAGE_STARTED)
                .withStyle(ChatFormatting.LIGHT_PURPLE), false);
    }

    public static void makeQuestion(Player player, IQuizzingPlayer quiz) {
        var component = new TranslatableComponent(MESSAGE_QUESTION, quiz.getQuestion()).withStyle(ChatFormatting.AQUA).append("\n");

        for (var q : quiz.getAnswers()) {
            component.append(new TranslatableComponent(MESSAGE_ANSWER_MARKED, q.getA()).withStyle(ChatFormatting.YELLOW))
                    .append(new TextComponent(q.getB()).withStyle(ChatFormatting.GREEN)).append("\n");
        }

        component.append(new TranslatableComponent(MESSAGE_QUESTION_LAST).withStyle(ChatFormatting.AQUA));

        player.createCommandSourceStack().sendSuccess(component, false);
    }

    public static void broadcast(Player self, Component message) {
        if (self.level.isClientSide) {
            return;
        }

        var server = self.level.getServer();
        server.createCommandSourceStack().sendSuccess(message, true);

        var players = server.getPlayerList().getPlayers();
        for (var p : players) {
            if (p.getUUID().equals(self.getUUID())) {
                continue;
            }
            p.sendMessage(message, Util.NIL_UUID);
        }
    }
}
