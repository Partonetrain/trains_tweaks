package info.partonetrain.trains_tweaks.feature.utilitycommands;

import com.mojang.brigadier.CommandDispatcher;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;

public class KillNonPlayersCommand {
    public KillNonPlayersCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(((Commands.literal("kill_non_players").requires((commandSourceStack) -> {
            return commandSourceStack.hasPermission(2);
        })).executes((commandContext) -> {
            return killNonPlayers((CommandSourceStack)commandContext.getSource());
        })));
    }

    private static int killNonPlayers(CommandSourceStack source) {
        try {
            Iterable<Entity> targets = source.getLevel().getAllEntities();

            int count = 0;
            for (Entity e : targets) {
                if (e != null && ! (e instanceof Player))
                {
                    //Constants.LOG.info("killing " + e.getType().getDescription().getString() + " (" + e.getStringUUID() + ")");
                    //Constants.LOG.info(count + " ");
                    e.remove(Entity.RemovalReason.DISCARDED);
                    count++;
                }
            }
            int finalCount = count;
            source.sendSuccess(() -> {
                return Component.translatable("commands.trains_tweaks.kill_non_players.success", finalCount);
            }, true);
            return count;
        }
        catch (Exception e){
            Constants.LOG.info(e.getMessage());
        }
        return -1;
    }
}
