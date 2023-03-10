package games.moegirl.sinocraft.sinocore.old.api.utility;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionNode;

import java.util.function.Predicate;

public class PermissionHelper {
    public static Predicate<CommandSourceStack> hasPermission(PermissionNode<Boolean> node) {
        return stack -> {
            if (stack.getEntity() instanceof ServerPlayer player) {
                return PermissionAPI.getPermission(player, node);
            } else {
                return true;
            }
        };
    }
}
