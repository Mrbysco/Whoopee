package com.mrbysco.whoopee.datagen;

import com.mrbysco.whoopee.datagen.assets.WhoopeeLanguageProvider;
import com.mrbysco.whoopee.datagen.assets.WhoopeeModelProvider;
import com.mrbysco.whoopee.datagen.assets.WhoopeeSoundProvider;
import com.mrbysco.whoopee.datagen.data.WhoopeeLootProvider;
import com.mrbysco.whoopee.datagen.data.WhoopeeRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class WhoopeeDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new WhoopeeRecipeProvider.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new WhoopeeLootProvider(packOutput, lookupProvider));

		generator.addProvider(true, new WhoopeeLanguageProvider(packOutput));
		generator.addProvider(true, new WhoopeeSoundProvider(packOutput));
		generator.addProvider(true, new WhoopeeModelProvider(packOutput));

	}
}
