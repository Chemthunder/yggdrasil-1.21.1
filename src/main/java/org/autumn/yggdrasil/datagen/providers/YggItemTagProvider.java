package org.autumn.yggdrasil.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.index.YggItemTags;
import org.autumn.yggdrasil.core.util.ToxicationEffectResourceReloadListener;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class YggItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public YggItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        for (ToxicationEffect effect : ToxicationEffectResourceReloadListener.collectEffects()) {
            this.getOrCreateTagBuilder(YggItemTags.ACCEPTABLE)
                    .add(effect.source())
                    .setReplace(false);
        }
    }
}
