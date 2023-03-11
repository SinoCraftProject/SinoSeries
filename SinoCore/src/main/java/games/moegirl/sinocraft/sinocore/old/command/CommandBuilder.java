package games.moegirl.sinocraft.sinocore.old.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A builder for minecraft command.
 */
public class CommandBuilder {

    final Node<LiteralArgumentBuilder<CommandSourceStack>> command;
    final String name;
    Node<?> current;

    public CommandBuilder(String name) {
        command = new Node<>(null, Commands.literal(name));
        current = command;
        this.name = name;
    }

    /**
     * Append an argument
     *
     * @param name argument name
     * @param type argument type
     * @param <T>  argument type
     * @return command builder
     */
    public <T> CommandBuilder then(String name, ArgumentType<T> type) {
        current = current.then(name, type);
        return this;
    }

    /**
     * Append a sub command
     *
     * @param name command name
     * @return command builder
     */
    public CommandBuilder then(String name) {
        current = current.then(name);
        return this;
    }

    /**
     * Add an executed
     *
     * @param command executed command
     * @return command builder
     */
    public CommandBuilder execute(Command<CommandSourceStack> command) {
        current.thisCmd.executes(command);
        return this;
    }

    /**
     * Add an executed
     *
     * @param command executed command
     * @return command builder
     */
    public CommandBuilder execute(Command2 command) {
        current.thisCmd.executes(command);
        return this;
    }

    /**
     * Add suggests to current argument
     *
     * @param provider suggests provider
     * @return command builder
     */
    public CommandBuilder suggests(SuggestionProvider<CommandSourceStack> provider) {
        if (current.isLiteral()) {
            throw new IllegalStateException("No argument in literal " + current.name());
        } else {
            current.getAsArgument().suggests(provider);
        }
        return this;
    }

    /**
     * Add requires to current argument or literal
     *
     * @param requirement requirement
     * @return command builder
     */
    public CommandBuilder requires(Predicate<CommandSourceStack> requirement) {
        current.thisCmd.requires(requirement);
        return this;
    }

    /**
     * Forward to previous argument, if not exist then forward to previous literal's last argument (or null if not exist)
     */
    public CommandBuilder forward() {
        if (!current.isRoot()) {
            current = current.prev;
        }
        return this;
    }

    /**
     * Forward to the nearest argument or literal named name
     *
     * @param name argument or literal name
     */
    public CommandBuilder forward(String name) {
        while (!name.equals(current.name())) {
            if (current.isRoot()) {
                break;
            }
            current = current.prev;
        }
        return this;
    }

    /**
     * Forward to the nearest literal
     */
    public CommandBuilder forwardLiteral() {
        if (!current.isRoot()) {
            current = current.prev;
            while (!current.isLiteral()) {
                current = current.prev;
            }
        }
        return this;
    }

    public CommandBuilder subCommand(Consumer<CommandBuilder> subCommand) {
        Node<?> prev = current;
        subCommand.accept(this);
        current = prev;
        return this;
    }

    public CommandBuilder subCommand(String name, Consumer<CommandBuilder> subCommand) {
        Node<?> prev = current;
        subCommand.accept(then(name));
        current = prev;
        return this;
    }

    public CommandBuilder apply(Consumer<CommandBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public LiteralArgumentBuilder<CommandSourceStack> build() {
        return command.build();
    }

    final static class Node<T extends ArgumentBuilder<CommandSourceStack, ?>> {

        final Node<?> prev;
        final T thisCmd;
        final List<Node<?>> children = new LinkedList<>();

        Node(Node<?> prev, T command) {
            this.prev = prev;
            this.thisCmd = command;
        }

        boolean isRoot() {
            return prev == null;
        }

        boolean isLiteral() {
            return thisCmd instanceof LiteralArgumentBuilder<?>;
        }

        @SuppressWarnings("unchecked")
        LiteralArgumentBuilder<CommandSourceStack> getAsLiteral() {
            return (LiteralArgumentBuilder<CommandSourceStack>) thisCmd;
        }

        boolean isArgument() {
            return thisCmd instanceof RequiredArgumentBuilder<?, ?>;
        }

        @SuppressWarnings("unchecked")
        RequiredArgumentBuilder<CommandSourceStack, ?> getAsArgument() {
            return (RequiredArgumentBuilder<CommandSourceStack, ?>) thisCmd;
        }

        String name() {
            return isArgument() ? getAsArgument().getName() : getAsLiteral().getLiteral();
        }

        Node<LiteralArgumentBuilder<CommandSourceStack>> then(String name) {
            LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal(name);
            Node<LiteralArgumentBuilder<CommandSourceStack>> child = new Node<>(this, literal);
            children.add(child);
            return child;
        }

        Node<RequiredArgumentBuilder<CommandSourceStack, ?>> then(String name, ArgumentType<?> type) {
            RequiredArgumentBuilder<CommandSourceStack, ?> argument = Commands.argument(name, type);
            Node<RequiredArgumentBuilder<CommandSourceStack, ?>> child = new Node<>(this, argument);
            children.add(child);
            return child;
        }

        T build() {
            if (!children.isEmpty()) {
                children.forEach(child -> thisCmd.then(child.build()));
            }
            return thisCmd;
        }
    }

    @FunctionalInterface
    public interface Command2 extends Command<CommandSourceStack> {

        void execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException;

        default int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            execute(context);
            return SINGLE_SUCCESS;
        }
    }
}
