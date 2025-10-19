package cx.rain.mc.sino.util;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextUtil {
    @SneakyThrows
    public static String getTextFrom(Path path) {
        if (!Files.exists(path) || Files.isDirectory(path)) {
            return "";
        }

        var str = Files.readString(path, StandardCharsets.UTF_8);
        return removeCR(str);
    }

    private static String removeCR(String str) {
        return str.replace("\r", "");
    }

    public static String escapeBreakline(String str) {
        return str.replace("\n", "\\n");
    }
}
