package games.moegirl.sinocraft.sinodivination.old.item;

import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WoodenChestItem extends BlockItem {

    private static final ThreadLocal<WoodenChest> chestHolder = new ThreadLocal<>();

    public static WoodenChestItem create(RegistryObject<? extends WoodenChest> chest) {
        chestHolder.set(chest.get());
        WoodenChestItem item = new WoodenChestItem();
        chestHolder.remove();
        return item;
    }

    private WoodenChestItem() {
        super(chestHolder.get(), new Properties());
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 300;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                consumer.accept(games.moegirl.sinocraft.sinodivination.old.client.WoodenChestRenderer.get(chestHolder.get())));
    }
}
