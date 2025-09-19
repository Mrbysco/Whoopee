package com.mrbysco.whoopee.datagen.data;

import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class WhoopeeRecipeProvider extends RecipeProvider {

	public WhoopeeRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
		super(provider, recipeOutput);
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

	public static class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			return new WhoopeeRecipeProvider(provider, recipeOutput);
		}

		@Override
		public String getName() {
			return "Whoopee Recipes";
		}
	}
}
