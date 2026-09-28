package org.chemthunder.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.item.BottledSapItem;
import org.chemthunder.yggdrasil.core.item.SylvaticusItem;

/**
 * @author Chemthunder
 */
public interface YggItems {
    ItemRegistrant rant = new ItemRegistrant(Yggdrasil.MOD_ID);

    Item BOTTLED_SAP = rant.register("bottled_sap", BottledSapItem::new, new Item.Settings()
            .maxCount(1)
            .food(new FoodComponent.Builder().alwaysEdible().build())
    );

    Item SYLVATICUS = rant.register("sylvaticus", SylvaticusItem::new, new Item.Settings()
            .maxCount(1)
    );

    static void init() {}
}
