package com.mrbysco.whoopee.datagen.data;

import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class WhoopeeLootProvider {

	public static SingleRegistryBootstrap<LootTable> create() {
		return new LootTableProvider(
				BuiltInLootTables.all(),
				List.of(
						new LootTableProvider.SubProviderEntry(SlabBlockLoot::new, LootContextParamSets.BLOCK)
				)
		);
	}

	private static class SlabBlockLoot extends BlockLootSubProvider {

		protected SlabBlockLoot(LootTableSubProvider.Context context) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
		}

		@Override
		protected void generate() {
			this.dropSelf(WhoopeeRegistry.WHOOPEE_BLOCK.get());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return WhoopeeRegistry.BLOCKS.getEntries().stream().map(holder -> (Block) holder.value())::iterator;
		}
	}
}
