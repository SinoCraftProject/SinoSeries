package games.moegirl.sinocraft.sinocore.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.commands.Commands.literal;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SCCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> SINOCORE_ROOT = literal("sinoseries")
            .then(ReloadTextureCommand.RELOAD)
            .then(VersionCommand.VERSION);

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(SINOCORE_ROOT);
    }
}
