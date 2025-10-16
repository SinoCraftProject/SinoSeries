package games.moegirl.sinocraft.sinocore.registry;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public interface ICommandRegistry extends IRegistrable<ICommandRegistry.Command> {

    void register();

    void register(Command command);

    interface Command {

        void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection);
    }

    interface SimpleCommand extends Command {

        void register(CommandDispatcher<CommandSourceStack> dispatcher);

        @Override
        default void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
            register(dispatcher);
        }
    }
}
