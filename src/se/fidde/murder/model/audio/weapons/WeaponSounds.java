package se.fidde.murder.model.audio.weapons;

import java.net.URL;

import se.fidde.murder.model.audio.Sounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.Weapons;

/**
 * Weapon sounds
 * 
 * @author fidde
 * 
 */
public enum WeaponSounds implements Sounds {
	KNIFE("knife.mp3", Weapons.KNIFE), AXE("axe.mp3", Weapons.AXE), WRENCH(
			"wrench.mp3", Weapons.WRENCH), ROPE("rope.mp3", Weapons.ROPE), BOMB(
			"bomb.mp3", Weapons.BOMB), POISON("poison.mp3", Weapons.POISON), REVOLVER(
			"revolver.mp3", Weapons.REVOLVER), LEAD_PIPE("leadPipe.mp3",
			Weapons.LEAD_PIPE), CANDLESTICK("candleStick.mp3",
			Weapons.CANDLESTICK), SYRINGE("syringe.mp3", Weapons.SYRINGE);

	private final URL URL;
	private final Card CARD;

	private WeaponSounds(String name, Card card) {
		this.URL = getClass().getResource(name);
		this.CARD = card;
	}

	@Override
	public String getUrl() {
		return URL.toString();
	}

	@Override
	public Card getCARD() {
		return CARD;
	}

}
