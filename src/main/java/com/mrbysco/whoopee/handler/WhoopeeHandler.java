package com.mrbysco.whoopee.handler;

import com.mrbysco.whoopee.config.WhoopeeConfig;
import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import com.mrbysco.whoopee.util.FartUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

public class WhoopeeHandler {

	@SubscribeEvent
	public void onFall(LivingFallEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (event.getDistance() >= 2 && livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(WhoopeeRegistry.WHOOPEE_BLOCK_ITEM.asItem()) &&
				FartUtil.canPlay(livingEntity.getRandom(), WhoopeeConfig.COMMON.fallingPlayerTootChance.getAsDouble())) {
			FartUtil.playFart(livingEntity.level(), livingEntity, true);
		}
	}

	@SubscribeEvent
	public void openInventory(PlayerContainerEvent.Open event) {
		if (FartUtil.canPlay(event.getEntity().getRandom(), WhoopeeConfig.COMMON.inventoryTootChance.getAsDouble()) &&
				event.getContainer().getItems().stream().anyMatch(stack -> stack.is(WhoopeeRegistry.WHOOPEE_BLOCK_ITEM.asItem()))) {
			FartUtil.playFart(event.getEntity().level(), event.getEntity(), false);
		}
	}
}
