package games.moegirl.sinocraft.sinocore.datagen.model;

import net.minecraft.resources.ResourceLocation;

/**
 * 描述一个模型文件
 */
public interface IModelFile {

    ResourceLocation getLocation();

    ResourceLocation getUncheckedLocation();
}
