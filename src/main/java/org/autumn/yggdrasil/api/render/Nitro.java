package org.autumn.yggdrasil.api.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

/**
 * @author n1tr0nr
 */
@SuppressWarnings("unused")
public class Nitro {
    public static void renderSolidColorCube(MatrixStack matrices, VertexConsumer vertices, int color, Vec3d center, float inflation) {
        MatrixStack.Entry entry = matrices.peek();
        float x0 = (float)(center.getX() + (double)0.5F - (double)inflation);
        float x1 = (float)(center.getX() + (double)0.5F + (double)inflation);
        float y0 = (float)(center.getY() + (double)0.5F - (double)inflation);
        float y1 = (float)(center.getY() + (double)0.5F + (double)inflation);
        float z0 = (float)(center.getZ() + (double)0.5F - (double)inflation);
        float z1 = (float)(center.getZ() + (double)0.5F + (double)inflation);
        renderQuad(entry, vertices, color, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1);
        renderQuad(entry, vertices, color, x1, y0, z0, x0, y0, z0, x0, y1, z0, x1, y1, z0);
        renderQuad(entry, vertices, color, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0);
        renderQuad(entry, vertices, color, x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1);
        renderQuad(entry, vertices, color, x0, y1, z1, x1, y1, z1, x1, y1, z0, x0, y1, z0);
        renderQuad(entry, vertices, color, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1);
    }

    private static void renderQuad(MatrixStack.Entry matrix, VertexConsumer vertices, int color, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
        renderVertex(matrix, vertices, color, x1, y1, z1);
        renderVertex(matrix, vertices, color, x2, y2, z2);
        renderVertex(matrix, vertices, color, x3, y3, z3);
        renderVertex(matrix, vertices, color, x4, y4, z4);
    }

    private static void renderVertex(MatrixStack.Entry matrix, VertexConsumer vertices, int color, float x, float y, float z) {
        int a = color >> 24 & 255;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        vertices.vertex(matrix, x, y, z).color(r, g, b, a).texture(0.0F, 0.0F).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    public static void texCube(MatrixStack matrices, VertexConsumer vertices, double x, double y, double z, float inflation, Vec2f timeOffset, float tileSize) {
        MatrixStack.Entry entry = matrices.peek();
        float x0 = (float)x + 0.5F - inflation;
        float x1 = (float)x + 0.5F + inflation;
        float y0 = (float)y + 0.5F - inflation;
        float y1 = (float)y + 0.5F + inflation;
        float z0 = (float)z + 0.5F - inflation;
        float z1 = (float)z + 0.5F + inflation;
        float u0 = 0.0F;
        float u1 = 1.0F;
        float v0 = 0.0F;
        float v1 = 1.0F;
        u0 *= tileSize;
        u1 *= tileSize;
        v0 *= tileSize;
        v1 *= tileSize;
        float textureOffsetX = timeOffset.x;
        v0 += textureOffsetX;
        v1 += textureOffsetX;
        float textureOffsetY = timeOffset.y;
        u0 += textureOffsetY;
        u1 += textureOffsetY;
        renderQuad(entry, vertices, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1, u0, v0, u1, v1);
        renderQuad(entry, vertices, x1, y0, z0, x0, y0, z0, x0, y1, z0, x1, y1, z0, u0, v0, u1, v1);
        renderQuad(entry, vertices, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0, u0, v0, u1, v1);
        renderQuad(entry, vertices, x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1, u0, v0, u1, v1);
        renderQuad(entry, vertices, x0, y1, z1, x1, y1, z1, x1, y1, z0, x0, y1, z0, u0, v0, u1, v1);
        renderQuad(entry, vertices, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1, u0, v0, u1, v1);
    }

    private static void renderQuad(MatrixStack.Entry matrix, VertexConsumer vertices, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float u1, float v1, float u2, float v2) {
        renderVertex(matrix, vertices, x1, y1, z1, u1, v1);
        renderVertex(matrix, vertices, x2, y2, z2, u2, v1);
        renderVertex(matrix, vertices, x3, y3, z3, u2, v2);
        renderVertex(matrix, vertices, x4, y4, z4, u1, v2);
    }

    private static void renderVertex(MatrixStack.Entry matrix, VertexConsumer vertices, float x, float y, float z, float u, float v) {
        vertices.vertex(matrix, x, y, z).color(1.0F, 1.0F, 1.0F, 1.0F).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    public static void renderTexturedSphere(MatrixStack matrices, VertexConsumer vertices, BlockPos center, float radius, int quality, float rotation) {
        float inflation = 1.0F;
        radius *= inflation;
        matrices.translate((float)center.getX(), (float)center.getY(), (float)center.getZ());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));

        for(int i = 0; i < quality; ++i) {
            float lat1 = (float)Math.PI * (-0.5F + (float)i / (float)quality);
            float lat2 = (float)Math.PI * (-0.5F + (float)(i + 1) / (float)quality);

            for(int j = 0; j < quality; ++j) {
                float lon1 = (float)((Math.PI * 2D) * (double)j / (double)quality);
                float lon2 = (float)((Math.PI * 2D) * (double)(j + 1) / (double)quality);
                float x1 = (float)((double)radius * Math.cos(lat1) * Math.cos(lon1));
                float y1 = (float)((double)radius * Math.sin(lat1));
                float z1 = (float)((double)radius * Math.cos(lat1) * Math.sin(lon1));
                float u1 = 1.0F - lon1 / ((float)Math.PI * 2F);
                float v1 = 1.0F - (lat1 + ((float)Math.PI / 2F)) / (float)Math.PI;
                float x2 = (float)((double)radius * Math.cos(lat1) * Math.cos(lon2));
                float z2 = (float)((double)radius * Math.cos(lat1) * Math.sin(lon2));
                float u2 = 1.0F - lon2 / ((float)Math.PI * 2F);
                float x3 = (float)((double)radius * Math.cos(lat2) * Math.cos(lon2));
                float y3 = (float)((double)radius * Math.sin(lat2));
                float z3 = (float)((double)radius * Math.cos(lat2) * Math.sin(lon2));
                float v3 = 1.0F - (lat2 + ((float)Math.PI / 2F)) / (float)Math.PI;
                float x4 = (float)((double)radius * Math.cos(lat2) * Math.cos(lon1));
                float z4 = (float)((double)radius * Math.cos(lat2) * Math.sin(lon1));
                renderQuad(matrices.peek(), vertices, x1, y1, z1, x2, y1, z2, x3, y3, z3, x4, y3, z4, u1, v1, u2, v1, u2, v3, u1, v3);
            }
        }

    }

    private static void renderQuad(MatrixStack.Entry matrix, VertexConsumer vertices, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float u1, float v1, float u2, float v2, float u3, float v3, float u4, float v4) {
        renderVertex(matrix, vertices, x1, y1, z1, u1, v1);
        renderVertex(matrix, vertices, x2, y2, z2, u2, v2);
        renderVertex(matrix, vertices, x3, y3, z3, u3, v3);
        renderVertex(matrix, vertices, x4, y4, z4, u4, v4);
    }

    public static void renderSkyBeam(MatrixStack matrices, VertexConsumer vertices, BlockPos center, float radius, int height, float time) {
        MatrixStack.Entry entry = matrices.peek();
        float x = (float)center.getX() + 0.5F;
        float z = (float)center.getZ() + 0.5F;
        float yStart = (float)center.getY();
        float yEnd = yStart + (float)height;
        float angle = time * 0.2F % 1.0F;
        float[][] corners = new float[][]{{x - radius, z - radius}, {x + radius, z - radius}, {x + radius, z + radius}, {x - radius, z + radius}};
        float u0 = 0.0F;
        float u1 = 1.0F;
        float v0 = angle;
        float v1 = angle + (float)height / 8.0F;

        for(int i = 0; i < 4; ++i) {
            float[] corner1 = corners[i];
            float[] corner2 = corners[(i + 1) % 4];
            renderBeamQuad(entry, vertices, corner1[0], yStart, corner1[1], corner2[0], yStart, corner2[1], corner2[0], yEnd, corner2[1], corner1[0], yEnd, corner1[1], u0, v0, u1, v1);
        }

    }

    private static void renderBeamQuad(MatrixStack.Entry matrix, VertexConsumer vertices, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float u1, float v1, float u2, float v2) {
        renderVertex(matrix, vertices, x1, y1, z1, u1, v1);
        renderVertex(matrix, vertices, x2, y2, z2, u2, v1);
        renderVertex(matrix, vertices, x3, y3, z3, u2, v2);
        renderVertex(matrix, vertices, x4, y4, z4, u1, v2);
    }

    public static void renderCone(MatrixStack matrices, VertexConsumer vertices, BlockPos center, float radius, int height, float time) {
        MatrixStack.Entry entry = matrices.peek();
        float x = (float) center.getX() + 0.5F;
        float z = (float) center.getZ() + 0.5F;
        float yBase = (float) center.getY();
        float yTip = yBase + (float) height;
        float angleOffset = time * 0.2F % 1.0F;
        float u0 = 0.0F;
        float u1 = 1.0F;
        int segments = 16;

        for (int i = 0; i < segments; ++i) {
            double angle1 = (Math.PI * 2D) * (double) i / (double) segments;
            double angle2 = (Math.PI * 2D) * (double) (i + 1) / (double) segments;
            float x1 = x + (float) Math.cos(angle1) * radius;
            float z1 = z + (float) Math.sin(angle1) * radius;
            float x2 = x + (float) Math.cos(angle2) * radius;
            float z2 = z + (float) Math.sin(angle2) * radius;
            float v1 = angleOffset + (float) height / 8.0F;
            renderBeamQuad(entry, vertices, x1, yBase, z1, x2, yBase, z2, x, yTip, z, x, yTip, z, u0, angleOffset, u1, v1);
        }

    }

    private static void renderVertex(MatrixStack.Entry matrix, VertexConsumer vertices, float x, float y, float z, float u, float v, float alpha, float red, float green, float blue) {
        vertices.vertex(matrix, x, y, z).color(red, green, blue, alpha).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(matrix, 0.0F, 1.0F, 0.0F);
    }
}
