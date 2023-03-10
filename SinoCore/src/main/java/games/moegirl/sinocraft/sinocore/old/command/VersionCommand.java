package games.moegirl.sinocraft.sinocore.old.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class VersionCommand {
    public static final LiteralCommandNode<CommandSourceStack> VERSION = literal("version")
            .executes(stack -> {
                stack.getSource().sendSuccess(new TextComponent("SinoCore Version: " + SinoCore.VERSION), true);
                return 1;
            })
            .build();
}
