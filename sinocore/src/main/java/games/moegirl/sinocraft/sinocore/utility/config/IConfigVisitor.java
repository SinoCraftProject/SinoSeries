package games.moegirl.sinocraft.sinocore.utility.config;

import java.io.IOException;
import java.nio.file.Path;

public interface IConfigVisitor {

    int getInteger(String key, int defaultValue);

    float getFloat(String key, float defaultValue);

    IConfigVisitor getObject(String key);

    void setInteger(String name, int value);

    void setFloat(String name, float value);

    // TODO 其他类型等需要再加

    void save(Path path) throws IOException;
}
