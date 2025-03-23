package games.moegirl.sinocraft.sinocore.utility.config;

import games.moegirl.sinocraft.sinocore.SinoCorePlatform;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigPack {

    private final Path configPath;
    private final Map<Path, IConfigVisitor> visitors = new HashMap<>();

    public ConfigPack(String modid) {
        configPath = SinoCorePlatform.getConfigFolder().resolve(modid);
    }

    public Configs getClientConfigs() throws IOException {
        return getJsonConfigs("client");
    }

    public Configs getCommonConfigs() throws IOException {
        return getJsonConfigs("common");
    }

    // todo 线程安全
    public Configs getJsonConfigs(String type) throws IOException {
        Path configFile = configPath.resolve(type + ".json");

        if (visitors.containsKey(configFile)) {
            return new Configs(configFile, visitors.get(configFile));
        }

        IConfigVisitor content = JsonVisitor.create(configFile);
        fixConfigFile(type, configFile, content);
        visitors.put(configFile, content);
        return new Configs(configFile, content);
    }

    private void fixConfigFile(String type, Path filePath, IConfigVisitor visitor) {
        // TODO 以后如果需要修改配置文件格式，在这里处理旧文件
    }
}
