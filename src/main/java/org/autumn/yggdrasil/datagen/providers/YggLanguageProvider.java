package org.autumn.yggdrasil.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.autumn.yggdrasil.core.index.YggItems;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class YggLanguageProvider extends FabricLanguageProvider {
    public YggLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(YggItems.BOTTLED_SAP, "Bottle of Sap");

        translationBuilder.add("itemGroup.yggdrasil", "Yggdrasil");
    }
}
