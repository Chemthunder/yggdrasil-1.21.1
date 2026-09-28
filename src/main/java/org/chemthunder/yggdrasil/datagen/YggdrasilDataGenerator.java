package org.chemthunder.yggdrasil.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.chemthunder.yggdrasil.datagen.providers.YggLanguageProvider;
import org.chemthunder.yggdrasil.datagen.providers.YggModelProvider;
import org.chemthunder.yggdrasil.datagen.providers.YggParticleProvider;
import org.chemthunder.yggdrasil.datagen.providers.YggToxicationEffectProvider;

public class YggdrasilDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(YggModelProvider::new);
        pack.addProvider(YggLanguageProvider::new);

        pack.addProvider(YggParticleProvider::new);

        pack.addProvider(YggToxicationEffectProvider::new);
    }
}
