package games.moegirl.sinocraft.sinotest.datagen;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TestTags {

    public static final TagKey<Block> TEST_BLOCK_TAG = tag(Registries.BLOCK, "test_block");

    public static final TagKey<Item> TEST_ITEM_TAG = tag(Registries.ITEM, "test_item");

    public static <T> TagKey<T> tag(ResourceKey<Registry<T>> registry, String name) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath(SinoTest.MODID, name));
    }
}
