package games.moegirl.sinocraft.sinocore.old.data.condition;

import games.moegirl.sinocraft.sinocore.old.capability.IQuizzingPlayer;
import games.moegirl.sinocraft.sinocore.old.capability.SCCapabilities;
import net.minecraft.world.entity.player.Player;

public class PlayerQuizzingHelper {
    public static boolean isCompleteSuccessfully(Player player, boolean detectSuccess, boolean lastCorrect) {
        var cap = player.getCapability(SCCapabilities.QUIZZING_PLAYER_CAPABILITY).resolve();

        if (cap.isEmpty()) {
            return false;
        }

        var quiz = cap.get();

        if (detectSuccess) {
            return isEnded(quiz) && isSuccessful(quiz);
        } else {
            if (lastCorrect) {
                return !isEnded(quiz) && !isSuccessful(quiz);
            } else {
                return isEnded(quiz) && isFailed(quiz);
            }
        }
    }

    public static boolean hasReachedMaxStage(IQuizzingPlayer quiz) {
        return quiz.getQuizStage() >= quiz.maxQuizStage();
    }

    public static boolean isEnded(IQuizzingPlayer quiz) {
        return hasReachedMaxStage(quiz) || !quiz.isQuizzing();
    }

    public static boolean isSuccessful(IQuizzingPlayer quiz) {
        return quiz.isSucceed();
    }

    public static boolean isFailed(IQuizzingPlayer quiz) {
        return !quiz.isSucceed();
    }
}
