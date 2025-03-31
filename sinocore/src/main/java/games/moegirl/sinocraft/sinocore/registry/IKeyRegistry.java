package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.function.BiConsumer;

public interface IKeyRegistry extends IRegistrable<KeyMapping> {

    /**
     * 注册快捷键
     * @param key 快捷键信息
     * @param action 快捷键行为，仅客户端触发
     */
    KeyMapping register(KeyMapping key, BiConsumer<KeyMapping, Minecraft> action);

    /**
     * 注册快捷键
     * @param key 快捷键信息
     * @param action 快捷键行为，仅客户端触发
     */
    KeyMapping register(KeyMapping key, Runnable action);
}
