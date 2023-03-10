package games.moegirl.sinocraft.sinocore.old.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class QuizModelConfig {
    public static final Config CONFIG;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder()
                .configure(Config::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    public static class Config {
        public final ForgeConfigSpec.IntValue MAX_STAGE;
        public final ForgeConfigSpec.BooleanValue ENABLED;
        public final ForgeConfigSpec.ConfigValue<String> DATA_URL;
        public final ForgeConfigSpec.BooleanValue RANK_ENABLED;
        public final ForgeConfigSpec.ConfigValue<String> RANK_URL;
        public final ForgeConfigSpec.ConfigValue<String> RANK_KEY;

        Config(ForgeConfigSpec.Builder builder) {
            builder.push("quiz");

            MAX_STAGE = builder.worldRestart().defineInRange("maxStage", 15, 3, 50);
            ENABLED = builder.worldRestart().define("enabled", false);
            DATA_URL = builder.worldRestart().define("url", "https://example.com/api/quiz/something");

            RANK_ENABLED = builder.worldRestart().define("rank_enabled", false);
            RANK_URL = builder.worldRestart().define("rank_url", "https://example.com/api/rank/something");
            RANK_KEY = builder.worldRestart().define("rank_key", "exampleKey");

            builder.pop();
        }
    }
}
