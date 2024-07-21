package com.mrbysco.whoopee;

import com.mojang.logging.LogUtils;
import com.mrbysco.whoopee.config.WhoopeeConfig;
import com.mrbysco.whoopee.handler.WhoopeeHandler;
import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(WhoopeeMod.MOD_ID)
public class WhoopeeMod {
	public static final String MOD_ID = "whoopee";
	public static final Logger LOGGER = LogUtils.getLogger();

	public WhoopeeMod(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, WhoopeeConfig.commonSpec);
		eventBus.register(WhoopeeConfig.class);

		WhoopeeRegistry.BLOCKS.register(eventBus);
		WhoopeeRegistry.ITEMS.register(eventBus);
		WhoopeeRegistry.CREATIVE_MODE_TABS.register(eventBus);
		WhoopeeRegistry.SOUND_EVENTS.register(eventBus);

		NeoForge.EVENT_BUS.register(new WhoopeeHandler());

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
