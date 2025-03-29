package games.moegirl.sinocraft.sinocore.utility.config;

import java.io.IOException;
import java.nio.file.Path;

public class Configs implements IConfigVisitor {

    private final Path filePath;
    private final IConfigVisitor visitor;

    public Configs(Path filePath, IConfigVisitor visitor) {
        this.filePath = filePath;
        this.visitor = visitor;
    }

    public void save() throws IOException {
        visitor.save(filePath);
    }

    // -------------------------------------------------------------------


    @Override
    public int getInteger(String key, int defaultValue) {
        return visitor.getInteger(key, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return visitor.getFloat(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return visitor.getBoolean(key, defaultValue);
    }

    @Override
    public IConfigVisitor getObject(String key) {
        return visitor.getObject(key);
    }

    @Override
    public void setInteger(String name, int value) {
        visitor.setInteger(name, value);
    }

    @Override
    public void setFloat(String name, float value) {
        visitor.setFloat(name, value);
    }

    @Override
    public void setBoolean(String name, boolean value) {
        visitor.setBoolean(name, value);
    }

    @Override
    public void save(Path path) throws IOException {
        visitor.save(path);
    }
}
