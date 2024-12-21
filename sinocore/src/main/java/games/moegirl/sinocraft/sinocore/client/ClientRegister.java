package games.moegirl.sinocraft.sinocore.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ClientRegister {

    @ExpectPlatform
    public static void registerItemModelPredicate(ItemLike item, ResourceLocation id, ClampedItemPropertyFunction function) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemColor(ItemColor color, ItemLike... item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBlockColor(BlockColor color, Block... blocks) {
        throw new AssertionError();
    }
}
