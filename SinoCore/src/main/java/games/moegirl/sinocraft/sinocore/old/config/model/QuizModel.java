package games.moegirl.sinocraft.sinocore.old.config.model;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.cron.CronUtil;
import com.google.gson.Gson;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.config.QuizModelConfig;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class QuizModel {
    private static final Gson GSON = new Gson();
    private static QuizModel INSTANCE = null;

    public String url = QuizConstants.URL;

    public Calendar lastUpdated = Calendar.getInstance();

    public String date = "";

    public List<Question> questions = new ArrayList<>();

    public QuizModel() {
        INSTANCE = this;
    }

    public static QuizModel getInstance() {
        return INSTANCE;
    }

    public static class Question {
        public int id;
        public String question;
        public List<Answer> answers;

        public String question() {
            return question;
        }

        public List<Answer> answers() {
            return answers;
        }

        public static class Answer {
            public String answer;
            public String mark;
            public boolean isCorrect;

            public String answer() {
                return answer;
            }

            public boolean isCorrect() {
                return isCorrect;
            }
        }
    }

    public static QuizModel fetch() {
        var urlStr = QuizModelConfig.CONFIG.DATA_URL.get();

        try {
            var url = new URL(urlStr);

            var data = IOUtils.toString(url.toURI(), StandardCharsets.UTF_8);

            var ret = GSON.fromJson(data, QuizModel.class);
            INSTANCE = ret;

            ret.reFetch(false);
            return ret;
        } catch (Exception ex) {
            SinoCore.getLogger().warn("Cannot fetch URL from config file, don't forget to use /sinocore quiz load <URL> to set a new URL.");
        } finally {
            if (INSTANCE == null) {
                INSTANCE = new QuizModel();
            }

            CronUtil.schedule("* 2 * * *", (Runnable) () -> INSTANCE.reFetch(true));
        }

        return INSTANCE;
    }

    public Question random() {
        var rand = new Random().nextInt(0, questions.size());
        return questions.get(rand);
    }

    public void reFetch(boolean reload) {
        url = QuizConstants.URL;

        if (!QuizModelConfig.CONFIG.ENABLED.get()) {
            SinoCore.getLogger().warn("Quiz is not enabled!");
            return;
        }

        SinoCore.getInstance().getPool().execute(() -> INSTANCE.doReFetch(reload));
    }

    public void doReFetch(boolean reload) {
        SinoCore.getLogger().info("Re-fetching quiz data.");

        if (!reload) {
            if (url == null || url.equals("")) {
                url = QuizModelConfig.CONFIG.DATA_URL.get();
            }
        }

        try {
            var dataUrl = url;
            var url = new URL(dataUrl);

            AtomicReference<String> data = new AtomicReference<>();
            ThreadUtil.execute(() -> {
                try {
                    data.set(IOUtils.toString(url.toURI(), StandardCharsets.UTF_8));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            var model = GSON.fromJson(data.get(), QuizModel.class);

            lastUpdated = Calendar.getInstance();
            date = model.date;
            questions = model.questions;

            SinoCore.getLogger().info("Fetch successfully!");
        } catch (Exception ex) {
            SinoCore.getLogger().warn("Re-fetch failed, did you set the URL?");
            ex.printStackTrace();
        }
    }
}
