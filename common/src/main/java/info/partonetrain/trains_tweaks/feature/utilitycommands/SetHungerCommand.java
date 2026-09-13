package info.partonetrain.trains_tweaks.feature.utilitycommands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHungerCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("set_hunger")
                .requires(cs->cs.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("hunger", IntegerArgumentType.integer(0,20))
                                .then(Commands.argument("saturation", IntegerArgumentType.integer(0,20))
                                        .executes(context -> {
                                            ServerPlayer player = EntityArgument.getPlayer(context,"player");
                                            int hunger = IntegerArgumentType.getInteger(context, "hunger");
                                            int sat = IntegerArgumentType.getInteger(context, "saturation");
                                            player.getFoodData().setFoodLevel(hunger);
                                            player.getFoodData().setSaturation(sat);

                                            context.getSource().sendSuccess(() -> Component.literal("Set hunger to " + hunger + " and saturation to " + sat), false);
                                            return 0;
                                        })))
                )
        );
    }
}
