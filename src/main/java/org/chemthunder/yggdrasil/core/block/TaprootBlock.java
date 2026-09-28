package org.chemthunder.yggdrasil.core.block;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.chemthunder.yggdrasil.core.cca.world.TaprootComponent;
import org.chemthunder.yggdrasil.core.index.YggParticleTypes;
import org.chemthunder.yggdrasil.core.networking.s2c.SpawnScreenflashPayload;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class TaprootBlock extends Block {
    public TaprootBlock(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        TaprootComponent component = TaprootComponent.KEY.get(world);

        component.setActive(true);
        component.setPos(pos.toCenterPos());

        for (PlayerEntity player : world.getEntitiesByClass(PlayerEntity.class, new Box(pos).expand(150), entity -> true)) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, new SpawnScreenflashPayload(1.0F, 0xFF00ffa3));
            }
        }

        if (placer instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new SpawnScreenflashPayload(1.0F, 0xFF00ffa3));
        }
    }

    public void onBroken(WorldAccess access, BlockPos pos, BlockState state) {
        if (access instanceof World world) {
            TaprootComponent component = TaprootComponent.KEY.get(world);

            component.setActive(false);
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (random.nextBetween(1, 6) % 2 == 0) {
            for (int i = 0; i < world.random.nextBetween(1, 3); i++) {
                java.util.Random rand = new java.util.Random();

                float bound = 0.85F;

                Vec3d vPos = pos.toCenterPos();

                world.addParticle(
                        YggParticleTypes.TAPROOT_EMIT,
                        vPos.getX() + rand.nextFloat(-bound, bound),
                        vPos.getY() + rand.nextFloat(0.2F, 1.0F),
                        vPos.getZ() + rand.nextFloat(-bound, bound),
                        0,
                        0,
                        0
                );
            }
        }
    }
}
