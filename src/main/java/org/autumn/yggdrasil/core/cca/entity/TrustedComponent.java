package org.autumn.yggdrasil.core.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

/**
 * @author Chemthunder
 */
public class TrustedComponent implements AutoSyncedComponent {
    public static final ComponentKey<TrustedComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("trusted"),
            TrustedComponent.class
    );
    private final PlayerEntity player;

    private boolean trusted = false;

    public TrustedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        trusted = nbt.getBoolean("Trusted");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putBoolean("Trusted", trusted);
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
        sync();
    }
}
