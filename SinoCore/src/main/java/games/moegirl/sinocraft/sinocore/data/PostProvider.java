package games.moegirl.sinocraft.sinocore.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 在 DataProvider 最后运行，处理 SinoCore 注册的某些 DataProvider 产生的中间产物
 * <p><b>该类无需，也不应在任何地方手动实例化</b></p>
 *
 * @author luqin2007
 * @see games.moegirl.sinocraft.sinocore.mixin_inter.IDataGenerator
 * @see games.moegirl.sinocraft.sinocore.handler.TreeDataHandler
 * @see games.moegirl.sinocraft.sinocore.handler.WoodworkDataHandler
 */
public final class PostProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final List<Pair<? extends JsonElement, Path>> out = new ArrayList<>();
    private final List<Path> remove = new ArrayList<>();

    private PostProvider(String modid, PackOutput output) {
        this.modid = modid;
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        try {
            Path resFolder = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(modid);
            // languages
            Map<String, Pair<JsonObject, Path>> languageObjs = new HashMap<>();
            Files.walk(resFolder.resolve("lang"))
                    .filter(p -> p.getFileName().toString().startsWith("["))
                    .forEach(p -> addLanguage(p, languageObjs));

            // save all
            for (Path path : remove) {
                Files.deleteIfExists(path);
            }
            return CompletableFuture.allOf(out.stream()
                    .map(pair -> DataProvider.saveStable(cache, pair.getKey(), pair.getValue()))
                    .toArray(CompletableFuture[]::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLanguage(Path p, Map<String, Pair<JsonObject, Path>> languageObjs) {
        String name = p.getFileName().toString();
        String local = name.substring(name.lastIndexOf(']') + 1, name.lastIndexOf('.'));
        JsonObject object = languageObjs.computeIfAbsent(local, key -> {
            Path lp = p.getParent().resolve(local + ".json");
            JsonObject obj = Files.isRegularFile(lp) ? read(lp) : new JsonObject();
            Pair<JsonObject, Path> outPair = Pair.of(obj, lp);
            out.add(outPair);
            return outPair;
        }).getKey();
        read(p).entrySet().stream()
                .filter(entry -> !object.has(entry.getKey()))
                .forEach(entry -> object.add(entry.getKey(), entry.getValue()));
        remove.add(p);
    }

    private JsonObject read(Path p) {
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    @Override
    public String getName() {
        return "Post task " + modid;
    }
}
