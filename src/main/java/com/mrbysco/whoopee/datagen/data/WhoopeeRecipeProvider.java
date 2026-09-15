package com.mrbysco.whoopee.datagen.data;

import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;

import java.util.Set;

public class WhoopeeRecipeProvider extends RecipeProvider {

	public WhoopeeRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
		super(recipeOutput, advancementOutput);
	}

	public static MultiRegistryBootstrap create() {
		return new MultiRegistryBootstrap() {
			@Override
			public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
				return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
			}

			@Override
			public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
				new WhoopeeRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
			}
		};
	}

	@Override
	protected void buildRecipes() {
		shaped(RecipeCategory.MISC, WhoopeeRegistry.WHOOPEE_BLOCK.get())
				.pattern(" L ")
				.pattern("LRL")
				.pattern(" L ")
				.define('R', Tags.Items.DYES_RED)
				.define('L', Tags.Items.LEATHERS)
				.unlockedBy("has_leather", has(Tags.Items.LEATHERS))
				.unlockedBy("has_red_dye", has(Tags.Items.DYES_RED))
				.save(output);
	}
}
