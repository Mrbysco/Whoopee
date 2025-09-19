package com.mrbysco.whoopee.datagen.assets;

import com.mrbysco.whoopee.WhoopeeMod;
import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class WhoopeeModelProvider extends ModelProvider {

	public WhoopeeModelProvider(PackOutput output) {
		super(output, WhoopeeMod.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		blockModels.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(WhoopeeRegistry.WHOOPEE_BLOCK.get(),
								BlockModelGenerators.plainVariant(modLocation("block/whoopee_cushion"))
						)
						.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
	}
}
