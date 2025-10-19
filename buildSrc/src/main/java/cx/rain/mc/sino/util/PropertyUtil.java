package cx.rain.mc.sino.util;

import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertyUtil {
    public static @Nullable Object getPropertyRecursively(@NotNull Project project, @NotNull String name) {
        var property = project.findProperty(name);
        if (property != null) {
            return property;
        }

        var parent = project.getParent();
        if (parent != null) {
            return getPropertyRecursively(parent, name);
        }

        return null;
    }
}
