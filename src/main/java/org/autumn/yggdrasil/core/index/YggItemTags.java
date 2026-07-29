package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.autumn.yggdrasil.core.Yggdrasil;

/**
 * @author Chemthunder
 */
public interface YggItemTags {
    TagBuilder<Item> builder = new TagBuilder<>(Yggdrasil.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> ACCEPTABLE = builder.register("acceptable");
}
