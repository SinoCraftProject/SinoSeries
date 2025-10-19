package cx.rain.mc.sino.util;

import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ProjectUtil {
    public static boolean isModProject(@NotNull Project project) {
        return project.hasProperty(SinoGradleConstants.PROPERTY_MODID);
    }

    public static boolean isModPlatformProject(@NotNull Project project) {
        var parent = project.getParent();
        if (parent == null) {
            return false;
        }
        return isModProject(project.getParent()) && project.hasProperty(SinoGradleConstants.PROPERTY_PLATFORM);
    }

    private static Stream<Project> getProjects(Project project, Predicate<Project> filter) {
        return project.getChildProjects().values().stream().filter(filter);
    }

    public static List<Project> getAllMods(Project root) {
        return getProjects(root, ProjectUtil::isModProject).toList();
    }

    public static List<Project> getAllModPlatforms(Project root) {
        return getAllMods(root).stream()
                .flatMap(p -> getProjects(p, ProjectUtil::isModPlatformProject))
                .toList();
    }
}
