package games.moegirl.sinocraft.sinocore.data.gen.blockstate;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;

public interface IBlockStateBuilder {

    Block getOwner();

    JsonObject toJson();
}
