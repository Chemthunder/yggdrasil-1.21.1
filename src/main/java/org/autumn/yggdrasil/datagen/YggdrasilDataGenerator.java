package org.autumn.yggdrasil.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.autumn.yggdrasil.datagen.providers.*;

public class YggdrasilDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(YggItemTagProvider::new);

        pack.addProvider(YggModelProvider::new);
        pack.addProvider(YggLanguageProvider::new);

        pack.addProvider(YggParticleProvider::new);

        pack.addProvider(YggToxicationEffectProvider::new);
    }
}
