package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.ItemGroupRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;

/**
 * @author Chemthunder
 */
public interface YggItemGroups {
    ItemGroupRegistrant GROUPS = new ItemGroupRegistrant(Yggdrasil.MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Yggdrasil.id(Yggdrasil.MOD_ID));
    ItemGroup ITEM_GROUP = GROUPS.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
            .icon(() -> new ItemStack(YggItems.BOTTLED_SAP))
            .displayName(Text.translatable("itemGroup." + Yggdrasil.MOD_ID).withColor(0xFFfff3bd)) // 0xFF611437
            .build());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(YggItemGroups::addEntries);
    }

    private static void addEntries(FabricItemGroupEntries entries) {
        entries.add(YggItems.BOTTLED_SAP);

        for (ToxicationEffect effect : YggToxicationEffects.effects) {
            ItemStack stack = new ItemStack(YggItems.BOTTLED_SAP);

            stack.set(YggComponentTypes.TOX_EFFECT, effect);
            entries.add(stack);
        }
    }
}
