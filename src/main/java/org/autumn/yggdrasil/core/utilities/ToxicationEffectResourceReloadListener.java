package org.autumn.yggdrasil.core.utilities;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chemthunder
 */
public class ToxicationEffectResourceReloadListener implements SimpleSynchronousResourceReloadListener {
    public static final Map<Identifier, ToxicationEffect> EFFECTS = new HashMap<>();
    public static final Map<ToxicationEffect, Identifier> BACKWARDS = new HashMap<>();

    public Identifier getFabricId() {
        return Yggdrasil.id("toxication_effects");
    }

    public void reload(ResourceManager manager) {
        manager.findResources("toxication_effects", path -> path.getPath().endsWith(".json")).keySet().forEach(identifier -> {
            if (manager.getResource(identifier).isPresent()) {
                try (InputStream stream = manager.getResource(identifier).get().getInputStream()) {
                    var json = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                    DataResult<ToxicationEffect> result = ToxicationEffect.CODEC.parse(JsonOps.INSTANCE, json);

                    result.resultOrPartial(Yggdrasil.LOGGER::error).ifPresent(toxicationEffect -> {
                        EFFECTS.put(identifier, toxicationEffect);
                        BACKWARDS.put(toxicationEffect, identifier);
                    });
                } catch (Exception e) {
                    Yggdrasil.LOGGER.info("Failed to load file {}: {}", identifier, e.getMessage());
                }
            }
        });
    }
}
