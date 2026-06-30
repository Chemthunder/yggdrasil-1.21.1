package org.autumn.yggdrasil.core;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import org.autumn.yggdrasil.core.index.YggdrasilBlockEntityTypes;
import org.autumn.yggdrasil.core.index.YggdrasilBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Yggdrasil implements ModInitializer {
	public static final String PROJECT_ID = "yggdrasil";
	public static final Logger LOGGER = LoggerFactory.getLogger(PROJECT_ID);

	public void onInitialize() {
        YggdrasilBlockEntityTypes.init();
        YggdrasilBlocks.init();

		LOGGER.info("Init completed.");
	}

	public static Identifier id(String path) {
		return Identifier.of(PROJECT_ID, path);
	}
}
