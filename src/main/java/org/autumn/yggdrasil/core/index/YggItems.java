package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.item.BottledSapItem;

/**
 * @author Chemthunder
 */
public interface YggItems {
    ItemRegistrant rant = new ItemRegistrant(Yggdrasil.MOD_ID);

    Item BOTTLED_SAP = rant.register("bottled_sap", BottledSapItem::new, new Item.Settings()
            .maxCount(1)
            .food(new FoodComponent.Builder().alwaysEdible().build())
    );

    static void init() {}
}
