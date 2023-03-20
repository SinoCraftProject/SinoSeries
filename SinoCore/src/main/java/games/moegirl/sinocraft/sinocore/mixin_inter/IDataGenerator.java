package games.moegirl.sinocraft.sinocore.mixin_inter;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author luqin2007
 */
public interface IDataGenerator {

    void sinocoreSetPost(String modid, PackOutput output, ExistingFileHelper helper);
}
