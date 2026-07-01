package org.autumn.yggdrasil.core.client.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.autumn.yggdrasil.core.client.model.PlaneModel;
import org.autumn.yggdrasil.core.index.ModModelLayers;
import org.jetbrains.annotations.NotNull;

/**
 * @author Chemthunder
 */
public class SpinnyThingyFeature<T extends AbstractClientPlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    private final PlaneModel plane;

    public SpinnyThingyFeature(FeatureRendererContext<T, PlayerEntityModel<T>> context, EntityRendererFactory.@NotNull Context ctx) {
        super(context);
        this.plane = new PlaneModel(ctx.getPart(ModModelLayers.PLANE));
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity != null) {
            TrustedComponent trust = TrustedComponent.KEY.get(entity);

            if (trust.isTrusted()) {
                matrices.push();

                matrices.scale(1.2F, 1.2F, 1.2F);

                matrices.translate(0, -0.25F, 0);

                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(headYaw));

                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(headPitch));

                if (!entity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    matrices.translate(0, -0.6F, 0);
                } else {
                    matrices.translate(0, -0.4F, 0);
                }

                matrices.translate(0, -0.3F, 0);

                matrices.multiply(
                        RotationAxis.POSITIVE_Z.rotationDegrees(45)
                );

                matrices.multiply(
                        RotationAxis.POSITIVE_X.rotationDegrees(25)
                );

                matrices.translate(0.2F, -0.2F, 0);

                matrices.scale(0.8F, 0.8F, 0.8F);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age + tickDelta) * 4));

                plane.render(
                        matrices,
                        vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(Yggdrasil.id("textures/render/alt_sigil.png"))),
                        light,
                        OverlayTexture.DEFAULT_UV,
                        ColorHelper.Argb.withAlpha(25, 0xffffff)
                );

                plane.render(
                        matrices,
                        vertexConsumers.getBuffer(RenderLayer.getEyes(Yggdrasil.id("textures/render/alt_sigil.png"))),
                        light,
                        OverlayTexture.DEFAULT_UV,
                        0xffffff
                );

                matrices.pop();
            }
        }
    }
}
