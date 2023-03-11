package games.moegirl.sinocraft.sinocore.old.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A register for commands
 */
public class CommandRegister {

    private final List<Supplier<LiteralArgumentBuilder<CommandSourceStack>>> commands = new LinkedList<>();
    private final Set<String> cmdNames = new HashSet<>();
    private boolean isRegistered = false;

    /**
     * Call at constructor or init event
     */
    public void register() {
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
    }

    /**
     * Register a command
     *
     * @param name    command name
     * @param factory command factory
     * @return Registered command name.
     */
    public String register(String name,
                           Function<CommandBuilder, CommandBuilder> factory) {
        return register(name, () -> factory.apply(new CommandBuilder(name)).build());
    }

    /**
     * Register a command
     *
     * @param name    command name
     * @param factory command factory
     * @return Registered command name
     */
    public String register(String name,
                           Supplier<LiteralArgumentBuilder<CommandSourceStack>> factory) {
        if (isRegistered) {
            throw new IllegalStateException("Can't add more command when commands are registered");
        }
        if (cmdNames.contains(name)) {
            throw new IllegalArgumentException("Duplicated command registered: " + name);
        }
        commands.add(factory);
        cmdNames.add(name);
        return name;
    }

    private void registerCommands(RegisterCommandsEvent event) {
        isRegistered = true;
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        commands.stream()
                .map(Supplier::get)
                .forEach(dispatcher::register);
    }
}
