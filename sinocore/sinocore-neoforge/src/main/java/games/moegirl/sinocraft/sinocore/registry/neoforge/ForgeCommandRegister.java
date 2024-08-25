package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import games.moegirl.sinocraft.sinocore.registry.ICommandRegistry;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForgeCommandRegister implements ICommandRegistry {

    private final List<Command> commands = new ArrayList<>();

    @Override
    public void registerCommands() {
        MinecraftForge.EVENT_BUS.addListener((Consumer<RegisterCommandsEvent>) event -> {
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
