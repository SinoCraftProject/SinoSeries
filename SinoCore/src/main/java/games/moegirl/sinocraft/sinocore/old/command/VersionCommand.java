package games.moegirl.sinocraft.sinocore.old.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.literal;

public class VersionCommand {
    public static final LiteralCommandNode<CommandSourceStack> VERSION = literal("version")
            .executes(stack -> {
                stack.getSource().sendSuccess(Component.literal("SinoCore Version: " + SinoCore.MODID), true);
                return 1;
            })
            .build();
}
