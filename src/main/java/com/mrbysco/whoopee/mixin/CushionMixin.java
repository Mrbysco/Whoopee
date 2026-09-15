package com.mrbysco.whoopee.mixin;

import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cushion.class)
public abstract class CushionMixin extends BlockAttachedEntity {

	@Shadow
	protected abstract ItemStack getCushionItemStackWithData();

	protected CushionMixin(EntityType<? extends BlockAttachedEntity> type, Level level) {
		super(type, level);
	}

	@Inject(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;)V")
	public void dropItem(ServerLevel level, Entity causedBy, CallbackInfo ci) {
		if (hasData(WhoopeeRegistry.WHOOPEED)) {
			ItemEntity itemEntity = this.spawnAtLocation(level, WhoopeeRegistry.WHOOPEE_BLOCK_ITEM.get());
			if (itemEntity != null && causedBy instanceof LightningBolt) {
				itemEntity.setInvulnerableTime(20);
			}
		}
	}
}
