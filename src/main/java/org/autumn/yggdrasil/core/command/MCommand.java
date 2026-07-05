package org.autumn.yggdrasil.core.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;

/**
 * @author Chemthunder
 */
public class MCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(CommandManager.literal("yggdrasil").requires(MCommand::isViable)
                .then(CommandManager.literal("pos").then(CommandManager.argument("a", BlockPosArgumentType.blockPos()).executes(context -> {
                    BlockPos pos = BlockPosArgumentType.getBlockPos(context, "a");

                    WorldComponent.KEY.get(context.getSource().getWorld()).setPos(new Vec3d(
                            pos.getX() + 1.0F,
                            pos.getY(),
                            pos.getZ() + 1.0F
                    ));
                    return 1;
                })))
                .then(CommandManager.literal("enable").executes(context -> {
                    WorldComponent.KEY.get(context.getSource().getWorld()).setPlaced(true);
                    return 1;
                }))
                .then(CommandManager.literal("reset").executes(context -> {
                    WorldComponent w = WorldComponent.KEY.get(context.getSource().getWorld());
                    w.reset();
                    return 1;
                }))
                .then(CommandManager.literal("trust").executes(context -> {
                    TrustedComponent.KEY.get(context.getSource().getPlayer()).setTrusted(true);
                    return 1;
                }))
                .then(CommandManager.literal("untrust").executes(context -> {
                    TrustedComponent.KEY.get(context.getSource().getPlayer()).setTrusted(false);
                    return 1;
                }))
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
