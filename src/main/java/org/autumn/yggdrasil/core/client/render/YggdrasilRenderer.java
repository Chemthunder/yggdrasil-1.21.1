package org.autumn.yggdrasil.core.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec2f;
import org.autumn.yggdrasil.api.render.Nitrogen;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.autumn.yggdrasil.core.client.model.PlaneModel;
import org.autumn.yggdrasil.core.index.YggdrasilModelLayers;

public class YggdrasilRenderer {
    public static void createMain(
            WorldComponent c,
            WorldRenderer worldRenderer,
            VertexConsumerProvider immediate,
            MatrixStack matrices,
            RenderTickCounter tickCounter,
            Camera camera
    ) {



    }

    private static void createSigil(WorldComponent c, net.minecraft.client.util.math.MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Camera camera) {
        float x2 = (float) (c.getPos().x - (float) camera.getPos().x);
        float y2 = (float) (c.getPos().y - (float) camera.getPos().y);
        float z2 = (float) (c.getPos().z - (float) camera.getPos().z);

        matrices.push();

        EntityModelLoader re = MinecraftClient.getInstance().getEntityModelLoader();

        PlaneModel sigil = new PlaneModel(re.getModelPart(YggdrasilModelLayers.PLANE));

        sigil.render(
                matrices,
                immediate.getBuffer(RenderLayer.getEntityTranslucentEmissive(
                        Yggdrasil.id("textures/render/sigil.png")
                )),
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV,
                0xffffff
        );

        matrices.pop();
    }
}
