package games.moegirl.sinocraft.sinofeast.utility;

import games.moegirl.sinocraft.sinofeast.capability.SFCapabilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FoodTasteHelper {
    public static boolean isPrefer(Player player, ItemStack stack) {
        var cap = player.getCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE).orElseThrow(RuntimeException::new);
        return stack.is(cap.getPrefer().tasteKey());
    }

    public static boolean isLike(Player player, ItemStack stack) {
        var cap = player.getCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE).orElseThrow(RuntimeException::new);
        return stack.is(cap.getLike().tasteKey());
    }

    public static boolean isDislike(Player player, ItemStack stack) {
        var cap = player.getCapability(SFCapabilities.PLAYER_FOOD_PREFERENCE).orElseThrow(RuntimeException::new);
        return stack.is(cap.getDislike().tasteKey());
    }
}
