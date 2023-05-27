package games.moegirl.sinocraft.sinodivination.command;

import com.mojang.brigadier.CommandDispatcher;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/**
 * @author luqin2007
 */
public class SDCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(SinoDivination.MODID).then(Debug.command()));
    }
}
