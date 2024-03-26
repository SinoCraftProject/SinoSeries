package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import games.moegirl.sinocraft.sinocore.registry.ICommandRegistry;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

public class NeoForgeCommandRegister implements ICommandRegistry {

    private final List<Command> commands = new ArrayList<>();

    @Override
    public void registerCommands() {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            Commands.CommandSelection selection = event.getCommandSelection();
            CommandBuildContext context = event.getBuildContext();
            for (Command command : commands) {
                command.register(dispatcher, context, selection);
            }
        });
    }

    @Override
    public void registerCommand(Command command) {
        commands.add(command);
    }
}
