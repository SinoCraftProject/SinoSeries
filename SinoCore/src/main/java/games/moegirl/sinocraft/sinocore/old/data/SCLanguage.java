package games.moegirl.sinocraft.sinocore.old.data;

import games.moegirl.sinocraft.sinocore.old.command.QuizCommand;
import games.moegirl.sinocraft.sinocore.old.data.base.LanguageProviderBase;
import net.minecraft.data.PackOutput;

public class SCLanguage extends LanguageProviderBase {
    public SCLanguage(PackOutput output, String modIdIn) {
        super(output, modIdIn);
    }

    @Override
    protected void addTranslations() {
        add(QuizCommand.MESSAGE_NOT_PLAYER, "Please use this command with a player!", "这个命令只能由玩家使用！");
        add(QuizCommand.MESSAGE_NOT_STARTED, "Quiz is not started!", "问答还没有开始！");
        add(QuizCommand.MESSAGE_NOT_ENABLED, "Quiz is not enabled!", "问答挑战没有在本服务器启用。");
        add(QuizCommand.MESSAGE_SUCCEED, "Congratulations! You have passed the quiz.", "恭喜你已经通过了问答挑战！");
        add(QuizCommand.MESSAGE_BROADCAST_SUCCEED, "Player %s passed the quiz！", "玩家 %s 已经通过了问答挑战！");
        add(QuizCommand.MESSAGE_FAIL, "You failed the quiz.", "挑战失败！");
        add(QuizCommand.MESSAGE_WRONG_STATE, "Wrong game state!", "错误的游戏状态！");
        add(QuizCommand.MESSAGE_STARTED, "Quiz started!", "问~答~开始！");
        add(QuizCommand.MESSAGE_QUESTION, "Now listen to the question: %s", "下面请听新一道问题：%s");
        add(QuizCommand.MESSAGE_QUESTION_LAST, "Please step on the corresponding pressure plate.", "请踩在带有你认为正确的答案的选项的压力板上。");
        add(QuizCommand.MESSAGE_ANSWER_MARKED, "%s. ", "选项 %s、");
        add(QuizCommand.MESSAGE_ANSWER_WRONG, "Ah-oh, the answer is wrong.", "回~答~错误！");
        add(QuizCommand.MESSAGE_ANSWER_RIGHT, "Good answer.", "回答正确！");
        add(QuizCommand.MESSAGE_RANK_TITLE, "===== Rank of the quiz =====", "===== 答题数据排行榜 =====");
        add(QuizCommand.MESSAGE_RANK_BODY, "No.%s: %s Used %s second(s). Fail %s times.", "第 %s 名：%s  用时 %s 秒，失败次数：%s");
        add(QuizCommand.MESSAGE_RANK_FOOTER, "============================", "=======================");
        add(QuizCommand.MESSAGE_RANK_FETCH_FAILED, "Error while fetch rank information, contract the server owner!", "获取排行榜信息失败！请联系服务器管理员。");
        add(QuizCommand.MESSAGE_RANK_MY_BEST, "Your best result is %s second(s).", "你最好的答题成绩是：%s 秒！");
        add(QuizCommand.MESSAGE_FETCHING, "Getting rank list information, please wait...", "获取排行榜信息中，请稍候……");
    }
}
