package org.chemthunder.yggdrasil.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.ColorHelper;
import org.chemthunder.yggdrasil.core.Yggdrasil;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class ImpactFrameEvents {
    public static final int FRAMES = 3; // white 0, black 1, sigil 2, white 3

    public static int tickAge = 0;
    public static boolean active = false;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudRenderCallback.EVENT.register(new Render());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            if (active) {
                if (tickAge < FRAMES + 1) {
                    tickAge++;
                    if (tickAge == FRAMES) {
                        tickAge = 0;
                        active = false;
                    }
                }
            }
        }
    }

    public static class Render implements HudRenderCallback {
        public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
            if (active) {
                int color = 0;

                if (tickAge == 0) {
                    color = 0xFFffffff;
                }

                if (tickAge == 1) {
                    color = 0xFF000000;
                }

                if (tickAge == 2) {
                    context.drawTexture(
                            Yggdrasil.id("textures/render/sigil.png"),
                            0,
                            0,
                            0,
                            0,
                            context.getScaledWindowWidth(),
                            context.getScaledWindowHeight(),
                            context.getScaledWindowWidth(),
                            context.getScaledWindowHeight()
                    );
                }

                if (tickAge == 3) {
                    color = 0xFFffffff;
                }

                context.fill(
                        0,
                        0,
                        context.getScaledWindowWidth(),
                        context.getScaledWindowHeight(),
                        color
                );
            }
        }
    }
}
