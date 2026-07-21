package org.autumn.yggdrasil.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Chemthunder
 */
public class YCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(literal("yggdrasil").requires(YCommand::isViable)
                .then(literal("entity")
                        .then(literal("setPos").then(argument("pos", BlockPosArgumentType.blockPos()).executes(context -> {
                            BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");

                            WorldComponent.KEY.get(context.getSource().getWorld()).setPos(pos.toCenterPos());
                            return 1;
                        })))
                        .then(literal("reset").executes(context -> {
                            WorldComponent w = WorldComponent.KEY.get(context.getSource().getWorld());
                            w.reset(false);
                            return 1;
                        }).then(literal("skipAnim").executes(context -> {
                            WorldComponent w = WorldComponent.KEY.get(context.getSource().getWorld());
                            w.reset(true);
                            return 1;
                        })))

                        .then(literal("toggleState").then(argument("state", BoolArgumentType.bool()).executes(context -> {
                            WorldComponent w = WorldComponent.KEY.get(context.getSource().getWorld());
                            w.setPlaced(BoolArgumentType.getBool(context, "state"));
                            return 1;
                        })))
                )

                .then(literal("flags")
                        .then(literal("player:trusted").then(argument("target", EntityArgumentType.player()).then(argument("state", BoolArgumentType.bool()).executes(context -> {
                            PlayerEntity target = EntityArgumentType.getPlayer(context, "target");
                            TrustedComponent t = TrustedComponent.KEY.get(target);

                            t.setTrusted(BoolArgumentType.getBool(context, "state"));
                            return 1;
                        }))))
                        .then(literal("player:showhalo").then(argument("target", EntityArgumentType.player()).then(argument("state", BoolArgumentType.bool()).executes(context -> {
                            PlayerEntity target = EntityArgumentType.getPlayer(context, "target");
                            TrustedComponent t = TrustedComponent.KEY.get(target);

                            t.setShowHalo(BoolArgumentType.getBool(context, "state"));
                            return 1;
                        }))))
                        .then(literal("world:burning").then(argument("state", BoolArgumentType.bool()).executes(context -> {
                            WorldComponent w = WorldComponent.KEY.get(context.getSource().getWorld());
                            w.setBurning(BoolArgumentType.getBool(context, "state"));
                            return 1;
                        })))
                )
        );
    }

    private static boolean isViable(ServerCommandSource source) {
        if (source.getPlayer() != null) {
            if (TrustedComponent.KEY.get(source.getPlayer()).isTrusted() || source.hasPermissionLevel(2)) {
                return true;
            }
        }
        return false;
    }
}
