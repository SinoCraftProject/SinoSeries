package games.moegirl.sinocraft.sinocore.util;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

/**
 * 控制打开或关闭 OpenGL 功能
 *
 * @author luqin2007
 */
public abstract class GLSwitcher {

    private final boolean isEnabled;

    public GLSwitcher(int code) {
        this.isEnabled = GL11.glIsEnabled(code);
    }

    protected abstract void enableInternal();

    protected abstract void disableInternal();

    public GLSwitcher set(boolean enable) {
        if (enable) {
            enableInternal();
        } else {
            disableInternal();
        }
        return this;
    }

    public GLSwitcher enable() {
        enableInternal();
        return this;
    }

    public GLSwitcher disable() {
        disableInternal();
        return this;
    }

    public void resume() {
        if (isEnabled) {
            enableInternal();
        } else {
            disableInternal();
        }
    }

    public static GLSwitcher blend() {
        return new GLSwitcher(GL11.GL_DEPTH_TEST) {
            @Override
            protected void enableInternal() {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
            }

            @Override
            protected void disableInternal() {
                RenderSystem.disableBlend();
            }
        };
    }

    public static GLSwitcher depth() {
        return new GLSwitcher(GL11.GL_DEPTH_TEST) {
            @Override
            protected void enableInternal() {
                RenderSystem.enableDepthTest();
            }

            @Override
            protected void disableInternal() {
                RenderSystem.disableDepthTest();
            }
        };
    }
}
