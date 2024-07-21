package com.mrbysco.whoopee.util;

import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FartUtil {
	/**
	 * Check if the whoopee cushion can play a sound
	 *
	 * @param source the random source
	 * @param chance the chance to play the sound
	 * @return if the sound can play. If the chance is 0, it will always return false. If the chance is 1, it will always return true
	 */
	public static boolean canPlay(RandomSource source, double chance) {
		if (chance == 0) return false;
		if (chance >= 1) return true;
		return source.nextDouble() < chance;
	}

	/**
	 * Play a fart sound
	 *
	 * @param level  the level
	 * @param entity the entity
	 * @param reverb if the sound should have reverb
	 */
	public static void playFart(Level level, Entity entity, boolean reverb) {
		SoundEvent soundEvent = reverb ? WhoopeeRegistry.WHOOPEE_REVERB.get() : WhoopeeRegistry.WHOOPEE.get();
		if (!reverb && level.getRandom().nextDouble() <= 0.1) {
			soundEvent = WhoopeeRegistry.WHOOPEE_REVERB.get();
		}
		float f = 256.0F / 16.0F;
		if (entity instanceof Player player) {
			level.playSound(null, player, soundEvent, SoundSource.PLAYERS, f, 1.0F);
		} else {
			level.playSound(null, entity.blockPosition(), soundEvent, SoundSource.PLAYERS, f, 1.0F);
		}
		level.gameEvent(GameEvent.INSTRUMENT_PLAY, entity.position(), GameEvent.Context.of(entity));
	}
}
