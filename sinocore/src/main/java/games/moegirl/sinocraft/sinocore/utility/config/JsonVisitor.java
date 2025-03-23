package games.moegirl.sinocraft.sinocore.utility.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonVisitor implements IConfigVisitor {

    public static JsonVisitor create(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            return new JsonVisitor(new JsonObject());
        } else {
            String json = Files.readString(path);
            return new JsonVisitor(JsonParser.parseString(json).getAsJsonObject());
        }
    }

    private final JsonObject json;

    public JsonVisitor(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }

    @Override
    public int getInteger(String key, int defaultValue) {
        return json.has(key) ? json.get(key).getAsInt() : defaultValue;
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return json.has(key) ? json.get(key).getAsFloat() : defaultValue;
    }

    @Override
    public IConfigVisitor getObject(String key) {
        if (json.has(key)) {
            return new JsonVisitor(json.getAsJsonObject(key));
        } else {
            JsonObject object = new JsonObject();
            json.add(key, object);
            return new JsonVisitor(object);
        }
    }

    @Override
    public void setInteger(String name, int value) {
        json.addProperty(name, value);
    }

    @Override
    public void setFloat(String name, float value) {
        json.addProperty(name, value);
    }

    @Override
    public void save(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            Path parent = path.getParent();
            if (!Files.isDirectory(parent)) {
                Files.createDirectories(parent);
            }
            Files.createFile(path);
        }
        Files.writeString(path, json.toString());
    }
}
