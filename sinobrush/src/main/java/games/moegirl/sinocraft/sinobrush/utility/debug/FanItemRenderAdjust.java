package games.moegirl.sinocraft.sinobrush.utility.debug;

import com.mojang.blaze3d.platform.InputConstants;
import games.moegirl.sinocraft.sinobrush.client.FanRenderer;
import games.moegirl.sinocraft.sinocore.registry.IKeyRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.stream.Collectors;

import static games.moegirl.sinocraft.sinobrush.client.FanRenderer.MAX_DISPLAY_LINES;

/**
 * （调试用）使用按键微调每行文字的位置
 */
public class FanItemRenderAdjust {

    private static int currentIndex = 0;

    public static void register(IKeyRegistry keyRegistry) {
        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_move_up",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UP,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::up);

        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_move_down",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::down);

        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_move_left",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::left);

        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_move_right",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::right);

        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_rotate_l",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_COMMA,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::rl);

        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_rotate_r",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_PERIOD,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::rr);

        keyRegistry.register(new KeyMapping("key.sinobrush.fan_item_next",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_ENTER,
                "key.sinocore.category.sinobrush"), FanItemRenderAdjust::next);
    }

    public static void up() {
        FanRenderer.LINE_Y_IN_HAND[currentIndex] += 0.005;
    }

    public static void down() {
        FanRenderer.LINE_Y_IN_HAND[currentIndex] -= 0.005;
    }

    public static void left() {
        FanRenderer.LINE_X_IN_HAND[currentIndex] -= 0.005;
    }

    public static void right() {
        FanRenderer.LINE_X_IN_HAND[currentIndex] += 0.005;
    }

    public static void rl() {
        FanRenderer.LINE_R_IN_HAND[currentIndex] += 0.5;
    }

    public static void rr() {
        FanRenderer.LINE_R_IN_HAND[currentIndex] -= 0.5;
    }

    public static void next() {
        if (currentIndex == MAX_DISPLAY_LINES - 1) {
            System.out.println(Arrays.stream(FanRenderer.LINE_X_IN_HAND)
                    .mapToObj(f -> String.format("%.3f", f))
                    .collect(Collectors.joining(", ", "{", "}")));
            System.out.println(Arrays.stream(FanRenderer.LINE_Y_IN_HAND)
                    .mapToObj(f -> String.format("%.3f", f))
                    .collect(Collectors.joining(", ", "{", "}")));
            System.out.println(Arrays.stream(FanRenderer.LINE_R_IN_HAND)
                    .mapToObj(f -> String.format("%.1f", f))
                    .collect(Collectors.joining(", ", "{", "}")));
        } else {
            System.out.printf("[%d] [%.3f, %.3f // %.3f]%n", currentIndex, FanRenderer.LINE_X_IN_HAND[currentIndex], FanRenderer.LINE_Y_IN_HAND[currentIndex], FanRenderer.LINE_R_IN_HAND[currentIndex]);
        }
        currentIndex = (currentIndex + 1) % MAX_DISPLAY_LINES;
    }
}
