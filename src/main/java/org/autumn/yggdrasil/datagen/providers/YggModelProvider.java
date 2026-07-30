package org.autumn.yggdrasil.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.index.YggItems;

/**
 * @author Chemthunder
 */
public class YggModelProvider extends FabricModelProvider {
    public YggModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(YggItems.BOTTLED_SAP, Models.GENERATED);

        Models.GENERATED.upload(
                Yggdrasil.id("item/sap_mixture"),
                TextureMap.layer0(Yggdrasil.id("item/sap_mixture"))
                        .register(TextureKey.LAYER1, Yggdrasil.id("item/sap_mixture_overlay")),
                itemModelGenerator.writer
        );
    }
}
