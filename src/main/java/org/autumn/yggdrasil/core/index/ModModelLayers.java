package org.autumn.yggdrasil.core.index;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.client.model.PlaneModel;

/**
 * @author AcoYT
 */
@Environment(EnvType.CLIENT)
public interface ModModelLayers {
    EntityModelLayer PLANE = create("plane");

    private static EntityModelLayer create(String name) {
        return new EntityModelLayer(Yggdrasil.id(name), "main");
    }

    static void clientInit() {
        EntityModelLayerRegistry.registerModelLayer(PLANE, PlaneModel::getTexturedModelData);
    }
}
