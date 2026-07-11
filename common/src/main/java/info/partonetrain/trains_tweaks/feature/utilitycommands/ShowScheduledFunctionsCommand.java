package info.partonetrain.trains_tweaks.feature.utilitycommands;

import com.google.common.collect.Table;
import com.mojang.brigadier.CommandDispatcher;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.timers.TimerQueue;

public class ShowScheduledFunctionsCommand {
    public ShowScheduledFunctionsCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(((Commands.literal("show_scheduled_functions").requires((commandSourceStack) -> {
            return commandSourceStack.hasPermission(2);
        })).executes((commandContext) -> {
            return showScheduledFunctions((CommandSourceStack)commandContext.getSource());
        })));
    }

    private static int showScheduledFunctions(CommandSourceStack source) {
        try {
            int functions = 0;
            MutableComponent ret = Component.empty();

            TimerQueue<MinecraftServer> timerqueue = source.getServer().getWorldData().overworldData().getScheduledEvents();
            for(Table.Cell<String, Long, TimerQueue.Event<MinecraftServer>> i : timerqueue.events.cellSet()){
                String[] split1 = i.toString().split("=");
                String formatted = split1[0].replace("(", "").replace(")", "").replace(",", ", gametime: ");
                ret.append(functions + ": " + formatted + "\n");
                functions++;
            }

            source.sendSuccess(() -> {
                return ret;
            }, true);
            return functions;
        }
        catch (Exception e){
            Constants.LOG.info("Failed to show scheduled functions: " + e.getMessage());
        }
        return -1;
    }
}
