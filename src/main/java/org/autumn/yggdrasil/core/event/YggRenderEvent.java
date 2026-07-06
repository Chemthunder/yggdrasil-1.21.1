package org.autumn.yggdrasil.core.event;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.autumn.yggdrasil.api.render.Nitro;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.autumn.yggdrasil.core.client.model.PlaneModel;
import org.autumn.yggdrasil.core.index.ModModelLayers;

public class YggRenderEvent implements WorldRenderEvents.Last {
    public void onLast(WorldRenderContext worldRenderContext) {
        MatrixStack matrixStack = worldRenderContext.matrixStack();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        WorldComponent w = WorldComponent.KEY.get(MinecraftClient.getInstance().world);
        Vec3d pos = w.getPos();

        if (matrixStack != null && w.isPlaced()) {
            float x = (float) (pos.x - worldRenderContext.camera().getPos().x);
            float y = (float) (pos.y - worldRenderContext.camera().getPos().y);
            float z = (float) (pos.z - worldRenderContext.camera().getPos().z);

            float delta = worldRenderContext.tickCounter().getTickDelta(true);

            {
                matrixStack.push();

                matrixStack.translate(x, y + 0.5F, z);

                Nitro.texSphere(
                        matrixStack,
                        consumers.getBuffer(
                                RenderLayer.getEntityTranslucentCull(
                                        Yggdrasil.id("textures/render/orb.png")
                                )
                        ),
                        0,
                        0,
                        0,
                        6,
                        6,
                        (w.getAge() + delta) / 4
                );

                matrixStack.pop();
            }

            {
                matrixStack.push();

                PlaneModel sigil1 = new PlaneModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ModModelLayers.PLANE));

                matrixStack.translate(x, y + 25, z);

                int sf = 24;

                matrixStack.scale(sf, sf, sf);

                matrixStack.multiply(
                        RotationAxis.POSITIVE_X.rotationDegrees(90)
                );

                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(
                        (WorldComponent.KEY.get(MinecraftClient.getInstance().world).getAge() + delta) * 2)
                );

                sigil1.render(
                        matrixStack,
                        consumers.getBuffer(
                                RenderLayer.getEntityTranslucent(
                                        Yggdrasil.id("textures/render/sigil.png")
                                )
                        ),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV
                );
                matrixStack.pop();

                matrixStack.push();

                PlaneModel sigil2 = new PlaneModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ModModelLayers.PLANE));

                matrixStack.translate(x, y + 25, z);

                matrixStack.scale(sf, sf, sf);

                matrixStack.multiply(
                        RotationAxis.POSITIVE_X.rotationDegrees(90)
                );

                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(
                        (WorldComponent.KEY.get(MinecraftClient.getInstance().world).getAge() + delta) * -2)
                );

                sigil2.render(
                        matrixStack,
                        consumers.getBuffer(
                                RenderLayer.getEntityTranslucent(
                                        Yggdrasil.id("textures/render/sigil.png")
                                )
                        ),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV
                );
                matrixStack.pop();
            }

            {
                int rotDivisor = 18;

                matrixStack.push();

                matrixStack.translate(x - 0.5F, y, z - 0.5F);

                Nitro.texCube(
                        matrixStack,
                        consumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(
                                Yggdrasil.id("textures/render/new_tile_3.png")
                        )),
                        0,
                        0,
                        0,
                        w.getRadius(),
                        new Vec2f((w.getAge() + delta) / rotDivisor, 0),
                        32
                );

                matrixStack.pop();
            }
        }
    }
}
