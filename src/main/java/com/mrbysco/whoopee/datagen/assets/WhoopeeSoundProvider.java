package com.mrbysco.whoopee.datagen.assets;

import com.mrbysco.whoopee.WhoopeeMod;
import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class WhoopeeSoundProvider extends SoundDefinitionsProvider {

	public WhoopeeSoundProvider(PackOutput packOutput) {
		super(packOutput, WhoopeeMod.MOD_ID);
	}

	@Override
	public void registerSounds() {
		this.add(WhoopeeRegistry.WHOOPEE, definition()
				.subtitle(modSubtitle(WhoopeeRegistry.WHOOPEE.getId()))
				.with(
						sound(modLoc("whoopee")),
						sound(modLoc("whoopee2"))
				));
		this.add(WhoopeeRegistry.WHOOPEE_REVERB, definition()
				.subtitle(modSubtitle(WhoopeeRegistry.WHOOPEE.getId()))
				.with(
						sound(modLoc("whoopee-reverb")),
						sound(modLoc("whoopee2-reverb"))
				));
	}


	public String modSubtitle(ResourceLocation id) {
		return WhoopeeMod.MOD_ID + ".subtitle." + id.getPath();
	}

	public ResourceLocation modLoc(String name) {
		return WhoopeeMod.modLoc(name);
	}
}
