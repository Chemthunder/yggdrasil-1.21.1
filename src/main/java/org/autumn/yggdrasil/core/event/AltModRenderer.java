package org.autumn.yggdrasil.core.event;

import com.nitron.nitrogen.render.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import org.autumn.yggdrasil.api.render.Nitrogen;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;

public class AltModRenderer implements WorldRenderEvents.Last {
    public void onLast(WorldRenderContext worldRenderContext) {
        RenderTickCounter tickCounter = worldRenderContext.tickCounter();
        MatrixStack matrices = worldRenderContext.matrixStack();
        WorldComponent c = WorldComponent.KEY.get(MinecraftClient.getInstance().world);
        Camera camera = worldRenderContext.camera();
        VertexConsumerProvider immediate = worldRenderContext.consumers();

        float delta = tickCounter.getTickDelta(false);

        float x = (float) (c.getPos().x - (float) camera.getPos().x);
        float y = (float) (c.getPos().y - (float) camera.getPos().y);
        float z = (float) (c.getPos().z - (float) camera.getPos().z);

        int f = 18;

        matrices.push();

        matrices.translate(x, y, z);

        Nitrogen.texCube(
                matrices,
                immediate.getBuffer(RenderLayer.getEntityTranslucentEmissive(
                        Yggdrasil.id("textures/render/tile_0.png")
                )),
                x, y, z,
                50,
                new Vec2f((c.getAge() + delta) / -f, (c.getAge() + delta) / -f),
                32
        );

        matrices.pop();
    }
}
