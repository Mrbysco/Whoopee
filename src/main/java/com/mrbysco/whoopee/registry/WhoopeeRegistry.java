package com.mrbysco.whoopee.registry;

import com.mrbysco.whoopee.WhoopeeMod;
import com.mrbysco.whoopee.block.WhoopeeBlock;
import com.mrbysco.whoopee.item.WhoopeeItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class WhoopeeRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WhoopeeMod.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WhoopeeMod.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WhoopeeMod.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, WhoopeeMod.MOD_ID);

	public static final DeferredBlock<WhoopeeBlock> WHOOPEE_BLOCK = BLOCKS.registerBlock("whoopee_cushion", WhoopeeBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.1F).sound(SoundType.WOOL));
	public static final DeferredItem<BlockItem> WHOOPEE_BLOCK_ITEM = ITEMS.registerItem("whoopee_cushion", (properties) -> new WhoopeeItem(WHOOPEE_BLOCK.get(), properties.useBlockDescriptionPrefix()));

	public static final DeferredHolder<SoundEvent, SoundEvent> WHOOPEE = registerSound("whoopee");
	public static final DeferredHolder<SoundEvent, SoundEvent> WHOOPEE_REVERB = registerSound("whoopee.reverb");

	private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String soundID) {
		return SOUND_EVENTS.register(soundID, () -> SoundEvent.createVariableRangeEvent(WhoopeeMod.modLoc(soundID)));
	}

	public static final Supplier<CreativeModeTab> WHOOPEE_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> WHOOPEE_BLOCK_ITEM.get().getDefaultInstance())
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.title(Component.translatable("itemGroup.whoopee.tab"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = ITEMS.getEntries().stream()
						.map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
