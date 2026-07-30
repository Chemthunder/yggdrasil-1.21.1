package org.autumn.yggdrasil.core;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.autumn.yggdrasil.core.command.YCommand;
import org.autumn.yggdrasil.core.index.YggComponentTypes;
import org.autumn.yggdrasil.core.index.YggItemGroups;
import org.autumn.yggdrasil.core.index.YggItems;
import org.autumn.yggdrasil.core.index.YggParticleTypes;
import org.autumn.yggdrasil.core.util.ToxicationEffectResourceReloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Yggdrasil implements ModInitializer {
	public static final String MOD_ID = "yggdrasil";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(new YCommand());

        YggItems.init();
        YggComponentTypes.init();
        YggParticleTypes.init();
        YggItemGroups.init();

        ALib.registerModMenu(MOD_ID, 0xFFc6fc6f);

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ToxicationEffectResourceReloadListener());

		LOGGER.info("Init completed.");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
