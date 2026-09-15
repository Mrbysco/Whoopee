package com.mrbysco.whoopee.datagen;

import com.mrbysco.whoopee.WhoopeeMod;
import com.mrbysco.whoopee.datagen.assets.WhoopeeLanguageProvider;
import com.mrbysco.whoopee.datagen.assets.WhoopeeModelProvider;
import com.mrbysco.whoopee.datagen.assets.WhoopeeSoundProvider;
import com.mrbysco.whoopee.datagen.data.WhoopeeLootProvider;
import com.mrbysco.whoopee.datagen.data.WhoopeeRecipeProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber
public class WhoopeeDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();

		event.createReloadableRegistryObjects(
				new RegistrySetBuilder()
						.add(RecipeProvider.asBootstrap(WhoopeeRecipeProvider::new))
						.add(Registries.LOOT_TABLE, WhoopeeLootProvider.create()),
				Set.of(WhoopeeMod.MOD_ID));

		generator.addProvider(true, new WhoopeeLanguageProvider(packOutput));
		generator.addProvider(true, new WhoopeeSoundProvider(packOutput));
		generator.addProvider(true, new WhoopeeModelProvider(packOutput));

	}
}
