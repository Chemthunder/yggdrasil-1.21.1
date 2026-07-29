package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;

/**
 * @author Chemthunder
 */
public interface YggComponentTypes {
    ComponentTypeRegistrant rant = new ComponentTypeRegistrant(Yggdrasil.MOD_ID);

    ComponentType<ToxicationEffect> TOX_EFFECT = rant.register(
            "toxication_effect",
            ToxicationEffect.CODEC,
            ToxicationEffect.PACKET_CODEC
    );

    static void init() {}
}
