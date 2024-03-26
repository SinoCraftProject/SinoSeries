package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.ICommandRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import java.util.ArrayList;
import java.util.List;

public class FabricCommandRegister implements ICommandRegistry {

    private final List<Command> commands = new ArrayList<>();

    @Override
    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            for (Command command : commands) {
                command.register(dispatcher, registryAccess, environment);
            }
        });
    }

    @Override
    public void registerCommand(Command command) {
        commands.add(command);
    }
}
