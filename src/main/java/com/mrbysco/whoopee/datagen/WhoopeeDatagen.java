package com.mrbysco.whoopee.datagen;

import com.mrbysco.whoopee.datagen.assets.WhoopeeLanguageProvider;
import com.mrbysco.whoopee.datagen.assets.WhoopeeModelProvider;
import com.mrbysco.whoopee.datagen.assets.WhoopeeSoundProvider;
import com.mrbysco.whoopee.datagen.data.WhoopeeLootProvider;
import com.mrbysco.whoopee.datagen.data.WhoopeeRecipeProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class WhoopeeDatagen {

	public static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
			.add(RecipeProvider.asBootstrap(WhoopeeRecipeProvider::new))
			.add(Registries.LOOT_TABLE, WhoopeeLootProvider.create());

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createReloadableRegistryObjects(RELOADABLE_BUILDER);

		event.createProvider(WhoopeeLanguageProvider::new);
		event.createProvider(WhoopeeSoundProvider::new);
		event.createProvider(WhoopeeModelProvider::new);
	}
}
