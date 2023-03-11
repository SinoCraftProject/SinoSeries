package games.moegirl.sinocraft.sinocore.old.capability;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Impl of capability IQuizzingPlayer.
 *
 * @author qyl27
 */
public class QuizzingPlayer implements IQuizzingPlayer {
    protected boolean inQuiz = false;
    protected boolean succeed = false;
    protected int quizStage = -1;
    protected int maxStage = -1;
    protected String question = "";
    protected BiMap<String, Pair<String, Boolean>> answers = HashBiMap.create(new HashMap<>());

    protected List<Integer> questionUsed = new ArrayList<>();
    protected long startTime = -1;

    @Override
    public boolean isQuizzing() {
        return inQuiz;
    }

    @Override
    public void setQuizzing(boolean quizzing) {
        inQuiz = quizzing;
    }

    @Override
    public boolean isSucceed() {
        return succeed;
    }

    @Override
    public void setSucceed(boolean isSucceed) {
        succeed = isSucceed;
    }

    @Override
    public int getQuizStage() {
        return quizStage;
    }

    @Override
    public int maxQuizStage() {
        return maxStage;
    }

    @Override
    public void setQuizStage(int stage) {
        quizStage = stage;
    }

    @Override
    public void setMaxQuizStage(int count) {
        maxStage = count;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(int id, String questionIn) {
        questionUsed.add(id);
        question = questionIn;
    }

    @Override
    public boolean questionHasUsed(int id) {
        return questionUsed.contains(id);
    }

    @Override
    public void clearQuestionUsed() {
        questionUsed.clear();
    }

    /**
     * Get all answers available.
     *
     * @return List of Pair<Mark, Answer>.
     */
    @Override
    public List<Pair<String, String>> getAnswers() {
        var list = new ArrayList<Pair<String, String>>();

        for (var entry : answers.entrySet()) {
            list.add(new Pair<>(entry.getKey(), entry.getValue().getA()));
        }

        return list;
    }

    @Override
    public void addAnswer(String answer, String mark, boolean isCorrect) {
        answers.put(mark, new Pair<>(answer, isCorrect));
    }

    @Override
    public void clearAnswers() {
        answers.clear();
    }

    @Override
    public String getCorrectMark() {
        for (var a : answers.entrySet()) {
            if (a.getValue().getB()) {
                return a.getKey();
            }
        }
        return "";
    }

    @Override
    public boolean isCorrect(String mark) {
        return answers.get(mark.toUpperCase()).getB();
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(long time) {
        startTime = time;
    }

    @Override
    public CompoundTag serializeNBT() {
        var nbt = new CompoundTag();

        var quiz = new CompoundTag();
        quiz.putBoolean("inQuiz", inQuiz);
        quiz.putBoolean("succeed", succeed);

        quiz.putLong("start", startTime);
        quiz.putIntArray("used", questionUsed);

        quiz.putInt("stage", quizStage);
        quiz.putInt("maxStage", maxStage);
        quiz.putString("question", question);

        var answersList = new ListTag();
        for (var answer : answers.entrySet()) {
            var ans = new CompoundTag();
            ans.putString("answer", answer.getKey());
            ans.putString("answerMark", answer.getValue().getA());
            ans.putBoolean("isCorrect", answer.getValue().getB());
            answersList.add(ans);
        }
        nbt.put("answers", answersList);

        nbt.put("quiz", quiz);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (!nbt.contains("quiz")) {
            return;
        }

        var quiz = nbt.getCompound("quiz");

        if (quiz.contains("inQuiz")) {
            inQuiz = quiz.getBoolean("inQuiz");
        }

        if (quiz.contains("succeed")) {
            succeed = quiz.getBoolean("succeed");
        }

        if (quiz.contains("start")) {
            startTime = quiz.getLong("start");
        }

        if (quiz.contains("used")) {
            questionUsed.addAll(Arrays.stream(quiz.getIntArray("used")).boxed().toList());
        }

        if (quiz.contains("stage")) {
            quizStage = quiz.getInt("stage");
        }

        if (quiz.contains("maxStage")) {
            maxStage = quiz.getInt("maxStage");
        }

        if (quiz.contains("question")) {
            question = quiz.getString("question");
        }

        if (quiz.contains("answers")) {
            for (var answer : quiz.getList("answers", 10)) {
                if (answer instanceof CompoundTag ans) {
                    if (ans.contains("answer")
                            && ans.contains("answerMark")
                            && ans.contains("isCorrect")) {
                        answers.put(ans.getString("answer"),
                                new Pair<>(ans.getString("answerMark"), ans.getBoolean("isCorrect")));
                    }
                }
            }
        }
    }
}
