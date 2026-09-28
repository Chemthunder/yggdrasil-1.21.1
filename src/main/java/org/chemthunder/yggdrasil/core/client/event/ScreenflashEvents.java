package org.chemthunder.yggdrasil.core.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class ScreenflashEvents {
    public static float opacity = 0.0F;
    public static int lastColor = 0xFF00ffa3;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudRenderCallback.EVENT.register(new Render());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            if (opacity > 0.0F) {
                opacity -= 0.02F;
            }
        }
    }

    public static class Render implements HudRenderCallback {
        public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
            if (opacity > 0.0F) {
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();

                context.setShaderColor(
                        1.0F,
                        1.0F,
                        1.0F,
                        opacity
                );

                context.fill(
                        0,
                        0,
                        context.getScaledWindowWidth(),
                        context.getScaledWindowHeight(),
                        50,
                        lastColor
                );

                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();

                context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
