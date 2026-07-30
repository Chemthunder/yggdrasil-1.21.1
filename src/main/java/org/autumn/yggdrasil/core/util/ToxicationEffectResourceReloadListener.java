package org.autumn.yggdrasil.core.util;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToxicationEffectResourceReloadListener implements SimpleSynchronousResourceReloadListener {
    public static final Map<Identifier, ToxicationEffect> EFFECTS = new HashMap<>();
    public static final Map<ToxicationEffect, Identifier> BACKWARDS = new HashMap<>();

    public Identifier getFabricId() {
        return Yggdrasil.id("toxication_effects");
    }

    public void reload(ResourceManager manager) {
        manager.findResources("toxication_effects", path -> path.getPath().endsWith(".json")).keySet().forEach(id -> {
            if (manager.getResource(id).isPresent()) {
                try (InputStream stream = manager.getResource(id).get().getInputStream()) {
                    var json = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                    DataResult<ToxicationEffect> result = ToxicationEffect.CODEC.parse(JsonOps.INSTANCE, json);

                    result.resultOrPartial(Yggdrasil.LOGGER::error).ifPresent(data -> {
                        EFFECTS.put(id, data);
                        BACKWARDS.put(data, id);
                    });
                } catch (Exception e) {
                    Yggdrasil.LOGGER.error("Failed to load effect file {}: {}", id, e.getMessage());
                }
            }
        });

        Yggdrasil.LOGGER.info("Loaded {} effects: {}", EFFECTS.size(), EFFECTS.keySet().stream().toList());
    }

    public static List<ToxicationEffect> collectEffects() {
        return new ArrayList<>(EFFECTS.values());
    }

    @Nullable
    public static ToxicationEffect findEffectFromItemStack(ItemStack stack) {
        Item item = stack.getItem();

        for (ToxicationEffect effect : collectEffects()) {
            if (effect.source() == item) {
                return effect;
            }
        }
        return null;
    }

    @Nullable
    public static ToxicationEffect findEffectFromItemStack(Item item) {
        for (ToxicationEffect effect : collectEffects()) {
            if (effect.source() == item) {
                return effect;
            }
        }
        return null;
    }
}