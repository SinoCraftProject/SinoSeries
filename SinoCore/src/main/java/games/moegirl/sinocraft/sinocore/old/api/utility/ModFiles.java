package games.moegirl.sinocraft.sinocore.old.api.utility;

import net.minecraftforge.fml.ModList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * A class to get classes jar or directory.
 */
public record ModFiles(Path root, FileSystem fs) {

    private static final Map<String, ModFiles> CACHE_BY_ID = new HashMap<>();
    private static final Map<URI, ModFiles> CACHE_BY_URI = new HashMap<>();

    // forge "union" scheme
    private static final FileSystemProvider UFSP = FileSystemProvider.installedProviders().stream()
            .filter(f -> "union".equalsIgnoreCase(f.getScheme())).findFirst().orElseThrow();

    /**
     * Get classes path from modid, require mod loaded
     *
     * @param modid modid
     * @return file
     * @throws URISyntaxException   if this URL is not formatted strictly according to
     *                              RFC2396 and cannot be converted to a URI.
     * @throws NullPointerException if this modid is not loaded.
     */
    public static ModFiles getFiles(String modid) throws URISyntaxException {
        if (CACHE_BY_ID.containsKey(modid)) {
            return CACHE_BY_ID.get(modid);
        }
        ModFiles files = getFiles(ModList.get().getModObjectById(modid).orElseThrow().getClass());
        CACHE_BY_ID.put(modid, files);
        return files;
    }

    /**
     * Get classes path from any class
     *
     * @param anyClass class in the mod
     * @return file
     * @throws URISyntaxException   if this URL is not formatted strictly according to
     *                              RFC2396 and cannot be converted to a URI.
     * @throws NullPointerException if this modid is not loaded.
     */
    public static ModFiles getFiles(Class<?> anyClass) throws URISyntaxException {
        URI uri = anyClass.getProtectionDomain().getCodeSource().getLocation().toURI();
        if (CACHE_BY_URI.containsKey(uri)) {
            return CACHE_BY_URI.get(uri);
        }
        FileSystem fs;
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            fs = FileSystems.getFileSystem(uri);
        } else {
            fs = UFSP.getFileSystem(uri);
        }
        ModFiles files = new ModFiles(fs.getPath("/"), fs);
        CACHE_BY_URI.put(uri, files);
        return files;
    }

    /**
     * Return a stream contains all files in the package
     *
     * @param packageName package name
     * @return the {@link Stream} of {@link Path}
     * @throws IOException if an I/O error is thrown when accessing the starting file.
     */
    public Stream<Path> forPackage(String packageName) throws IOException {
        return Files.walk(root.resolve("/" + packageName.replace(".", "/")));
    }

    /**
     * Return a stream contains all files in the package
     *
     * @param packageName package name
     * @param depth       the maximum number of directory levels to visit
     * @return the {@link Stream} of {@link Path}
     * @throws IOException if an I/O error is thrown when accessing the starting file.
     */
    public Stream<Path> forPackage(String packageName, int depth) throws IOException {
        return Files.walk(root.resolve("/" + packageName.replace(".", "/")), depth);
    }
}
