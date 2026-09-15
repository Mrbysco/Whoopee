package com.mrbysco.whoopee.handler;

import com.mrbysco.whoopee.config.WhoopeeConfig;
import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import com.mrbysco.whoopee.util.FartUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

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
	public void onInteract(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		if (target.is(EntityTypes.CUSHION)) {
			ItemStack mainStack = player.getMainHandItem();
			if (!target.hasData(WhoopeeRegistry.WHOOPEED) && mainStack.is(WhoopeeRegistry.WHOOPEE_BLOCK_ITEM.get())) {
				target.setData(WhoopeeRegistry.WHOOPEED, true);
				mainStack.consume(1, player);
				target.playSound(SoundEvents.CUSHION_PLACE);
				event.setCancellationResult(InteractionResult.SUCCESS);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onSit(EntityMountEvent event) {
		Entity entity = event.getEntityBeingMounted();
		if (event.isMounting() && entity.is(EntityTypes.CUSHION) && entity.hasData(WhoopeeRegistry.WHOOPEED)) {
			if (FartUtil.canPlay(entity.getRandom(), WhoopeeConfig.COMMON.sittingTootChance.getAsDouble())) {
				FartUtil.playFart(entity.level(), entity, false);
			}
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
