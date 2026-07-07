package org.autumn.yggdrasil.core.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import org.autumn.yggdrasil.core.YggdrasilClient;
import org.joml.Vector2d;

import java.util.List;
import java.util.Random;

public class UnworthyEvent implements HudRenderCallback {
    public static final List<String> STRINGS = List.of(
        "YOU ARE UNWORTHY.",
        "WHY DO YOU STILL HOLD ON.",
        "LET HER GO.",
        "THIS ISN'T YOURS.",
        "CONTROLLING A BUCKING ANIMAL."
    );

    public Vector2d pos0 = new Vector2d(0, 0);
    public Vector2d pos1 = new Vector2d(0, 0);
    public Vector2d pos2 = new Vector2d(0, 0);

    public int i0 = 0;
    public int i1 = 0;
    public int i2 = 0;

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        int age = YggdrasilClient.GLOBAL_AGE;
        Random random = new Random();

        List<Vector2d> vectors = List.of(pos0, pos1, pos2);

        if (age % 2 == 0) {
            pos0 = new Vector2d(
                    random.nextInt(10, context.getScaledWindowWidth() - 10),
                    random.nextInt(10, context.getScaledWindowHeight() - 10)
            );

            pos1 = new Vector2d(
                    random.nextInt(10, context.getScaledWindowWidth() - 10),
                    random.nextInt(10, context.getScaledWindowHeight() - 10)
            );

            pos2 = new Vector2d(
                    random.nextDouble(10, context.getScaledWindowWidth() - 10),
                    random.nextDouble(10, context.getScaledWindowHeight() - 10)
            );

            i0 = random.nextInt(STRINGS.size());
            i1 = random.nextInt(STRINGS.size());
            i2 = random.nextInt(STRINGS.size());
        }


        for (int i = 0; i < 3; i++) {
            context.getMatrices().push();

            context.getMatrices().translate(vectors.get(i).x, vectors.get(i).y, 0);
            context.getMatrices().scale(1.5F, 1.5F, 1.5F);

            context.drawText(
                    MinecraftClient.getInstance().textRenderer,
                    Text.literal(STRINGS.get(random.nextInt(STRINGS.size()))),
                    0,
                    0,
                    0xFFc6fc6f,
                    true
            );

            context.getMatrices().push();
        }
    }
}
