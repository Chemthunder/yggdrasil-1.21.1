package org.autumn.yggdrasil.core;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.minecraft.util.Identifier;
import org.autumn.yggdrasil.core.command.MCommand;
import org.autumn.yggdrasil.core.index.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Yggdrasil implements ModInitializer {
	public static final String PROJECT_ID = "yggdrasil";
	public static final Logger LOGGER = LoggerFactory.getLogger(PROJECT_ID);

	public void onInitialize() {
        ModBlocks.init();

        CommandRegistrationCallback.EVENT.register(new MCommand());

        ALib.registerModMenu(PROJECT_ID, 0xffc6fc6f);

		LOGGER.info("Init completed.");
	}

	public static Identifier id(String path) {
		return Identifier.of(PROJECT_ID, path);
	}
}
