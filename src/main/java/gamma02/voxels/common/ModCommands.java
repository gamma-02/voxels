package gamma02.voxels.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import gamma02.voxels.server.VoxelsServer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class ModCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> getArgumentBuilder(){
        return Commands.literal("voxels").then(Commands.literal("view").then(Commands.argument("player", EntityArgument.player()).executes((stack) -> {
            ServerPlayer player = EntityArgument.getPlayer(stack, "player");
            if(VoxelsServer.isPresent(player.getUUID())){
                stack.getSource().sendSuccess(new TextComponent(new TranslatableComponent("command.voxels.view_success").getString() + VoxelsServer.getVoxelCount(player.getUUID())), true);
            }else{
                stack.getSource().sendFailure(new TranslatableComponent("command.voxels.view_fail"));
            }
            return Command.SINGLE_SUCCESS;
        }))).then(Commands.literal("add").then(Commands.argument("player", EntityArgument.player()).then(Commands.argument("amount", IntegerArgumentType.integer()).executes((source) ->{
            VoxelsServer.addVoxels(EntityArgument.getPlayer(source, "player").getUUID(), IntegerArgumentType.getInteger(source, "amount"));
            return Command.SINGLE_SUCCESS;
        }))));//Can add more later
    }

}
