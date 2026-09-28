package org.chemthunder.yggdrasil.core.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.yggdrasil.api.render.Nitro;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.YggdrasilClient;
import org.chemthunder.yggdrasil.core.cca.world.TaprootComponent;
import org.chemthunder.yggdrasil.core.client.model.PlaneModel;
import org.chemthunder.yggdrasil.core.index.YggModelLayers;

/**
 * @author Chemthunder
 */
public class TaprootRenderEvent implements WorldRenderEvents.Last {
    public void onLast(WorldRenderContext context) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) return;

        TaprootComponent component = TaprootComponent.KEY.get(world);

        MatrixStack matrixStack = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();
        float delta = context.tickCounter().getTickDelta(false);

        if (matrixStack != null && consumers != null && component.getPos() != null && component.isActive()) {
            Vec3d pos = component.getPos();

            float x = (float) (pos.x - context.camera().getPos().x);
            float y = (float) (pos.y - context.camera().getPos().y);
            float z = (float) (pos.z - context.camera().getPos().z);

            EntityModelLoader loader = MinecraftClient.getInstance().getEntityModelLoader();

            {
                matrixStack.push();

                float sin = (float) Math.sin(MinecraftClient.getInstance().world.getTime() / 16.0);

                Nitro.texCube(
                        matrixStack,
                        consumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(
                                Yggdrasil.id("textures/render/new_tile_3.png")
                        )),
                        x,
                        y,
                        z,
                        75,
                        new Vec2f(sin, (YggdrasilClient.GLOBAL_AGE + delta) / 4),
                        32
                );

                matrixStack.pop();
            }

            {
                matrixStack.push();

                PlaneModel lowerSigil = new PlaneModel(loader.getModelPart(YggModelLayers.PLANE));

                matrixStack.translate(
                        x,
                        y - 0.3F,
                        z
                );

                matrixStack.multiply(
                        RotationAxis.NEGATIVE_Y.rotationDegrees((YggdrasilClient.GLOBAL_AGE + delta))
                );

                matrixStack.scale(3, 3, 3);

                lowerSigil.render(
                        matrixStack,
                        consumers.getBuffer(
                                RenderLayer.getEntityCutout(
                                        Yggdrasil.id("textures/render/sigil.png")
                                )
                        ),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        0xFFffffff
                );

                matrixStack.pop();
            }

            {
                matrixStack.push();

                PlaneModel upperSigil = new PlaneModel(loader.getModelPart(YggModelLayers.PLANE));

                matrixStack.translate(
                        x,
                        y + 2.0F,
                        z
                );

                matrixStack.multiply(
                        RotationAxis.NEGATIVE_X.rotationDegrees(90)
                );

                matrixStack.multiply(
                        RotationAxis.NEGATIVE_Z.rotationDegrees((YggdrasilClient.GLOBAL_AGE + delta) / 2)
                );

                matrixStack.scale(1.9F, 1.9F, 1.9F);

                upperSigil.render(
                        matrixStack,
                        consumers.getBuffer(
                                RenderLayer.getEntityTranslucentEmissive(
                                        Yggdrasil.id("textures/render/alt_sigil.png")
                                )
                        ),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        0xFFffffff
                );

                matrixStack.pop();
            }
        }
    }
}
