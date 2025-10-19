package cx.rain.mc.sino;

import cx.rain.mc.sino.util.SinoGradleConstants;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class SinoGradlePlugin implements Plugin<Project> {
	@Override
	public void apply(@NotNull Project project) {
        if (project.hasProperty(SinoGradleConstants.PROPERTY_PLATFORM)) {
            var platform = project.property(SinoGradleConstants.PROPERTY_PLATFORM);
            project.setProperty(SinoGradleConstants.PROPERTY_LOOM_PLATFORM, platform);
        }
    }
}
