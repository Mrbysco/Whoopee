package com.mrbysco.whoopee.config;

import com.mrbysco.whoopee.WhoopeeMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WhoopeeConfig {
	public static class Common {
		public final ModConfigSpec.DoubleValue stepTootChance;
		public final ModConfigSpec.DoubleValue fallTootChance;
		public final ModConfigSpec.DoubleValue fallingPlayerTootChance;
		public final ModConfigSpec.DoubleValue inventoryTootChance;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("general");

			stepTootChance = builder
					.comment("The chance for a toot sound to play when walking on a Whoopee Cushion [Default: 0.05]")
					.defineInRange("stepTootChance", 0.05, 0.001, 1);

			fallTootChance = builder
					.comment("The chance for a toot sound to play when falling on a Whoopee Cushion [Default: 1.0]")
					.defineInRange("fallTootChance", 1, 0.001, 1);

			fallingPlayerTootChance = builder
					.comment("The chance for a toot sound to play when the player falls while wearing a Whoopee Cushion on their head [Default: 0.4]")
					.defineInRange("fallingPlayerTootChance", 0.4, 0.001, 1);

			inventoryTootChance = builder
					.comment("The chance for a toot sound to play when an inventory is opened containing a Whoopee Cushion [Default: 0.15]")
					.defineInRange("inventoryTootChance", 0.15, 0.001, 1);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		WhoopeeMod.LOGGER.debug("Loaded Whoopee's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		WhoopeeMod.LOGGER.warn("Whoopee's config just got changed on the file system!");
	}
}
