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
import java.util.function.Consumer;

public class NeoForgeCommandRegister implements ICommandRegistry {

    private final List<Command> commands = new ArrayList<>();

    private final String modId;

    public NeoForgeCommandRegister(String modId) {
        this.modId = modId;
    }

    @Override
    public String modId() {
        return modId;
    }

    @Override
    public void register() {
        NeoForge.EVENT_BUS.addListener((Consumer<RegisterCommandsEvent>) event -> {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            Commands.CommandSelection selection = event.getCommandSelection();
            CommandBuildContext context = event.getBuildContext();
            for (Command command : commands) {
                command.register(dispatcher, context, selection);
            }
        });
    }

    @Override
    public void register(Command command) {
        commands.add(command);
    }
}
