package com.mrbysco.whoopee.datagen.assets;

import com.mrbysco.whoopee.WhoopeeMod;
import com.mrbysco.whoopee.registry.WhoopeeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class WhoopeeLanguageProvider extends LanguageProvider {

	public WhoopeeLanguageProvider(PackOutput packOutput) {
		super(packOutput, WhoopeeMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.whoopee.tab", "Whoopee");

		add("whoopee.cushion_tooltip", "Shift right-click to place on the ground, right-click to play a toot sound");

		addBlock(WhoopeeRegistry.WHOOPEE_BLOCK, "Whoopee Cushion");
		addSubtitle(WhoopeeRegistry.WHOOPEE, "Flatulence");
		addSubtitle(WhoopeeRegistry.WHOOPEE_REVERB, "Flatulence but with reverb");

		addConfig("title", "Whoopee Config", null);
		addConfig("general", "General", "General Settings");
		addConfig("stepTootChance", "Step Toot Chance", "The chance for a toot sound to play when walking on a Whoopee Cushion [Default: 0.05]");
		addConfig("fallTootChance", "Fall Toot Chance", "The chance for a toot sound to play when falling on a Whoopee Cushion [Default: 1.0]");
		addConfig("fallingPlayerTootChance", "Falling Player Toot Chance", "The chance for a toot sound to play when the player falls while wearing a Whoopee Cushion on their head [Default: 0.4]");
		addConfig("inventoryTootChance", "Inventory Toot Chance", "The chance for a toot sound to play when an inventory is opened containing a Whoopee Cushion [Default: 0.15]");
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event
	 * @param text  The subtitle text
	 */
	public void addSubtitle(Supplier<SoundEvent> sound, String text) {
		this.addSubtitle(sound.get(), text);
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	public void addSubtitle(SoundEvent sound, String text) {
		String path = WhoopeeMod.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, text);
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add("whoopee.configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add("whoopee.configuration." + path + ".tooltip", description);
	}
}
