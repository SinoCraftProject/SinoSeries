package games.moegirl.sinocraft.sinocore.crafting.block_ingredient;

import games.moegirl.sinocraft.sinocore.crafting.serializer.Serializer;
import net.minecraft.resources.ResourceLocation;

/**
 * 方块测试器类型
 *
 * @param name       测试类名
 * @param serializer 序列化工具
 * @param <T>        测试器类型
 * @author luqin2007
 */
public record BlockIngredientType<T extends BlockIngredient<T>>(ResourceLocation name, Serializer<T> serializer) {
}
