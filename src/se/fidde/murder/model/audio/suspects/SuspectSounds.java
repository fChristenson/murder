package se.fidde.murder.model.audio.suspects;

import java.net.URL;

import se.fidde.murder.model.audio.Sounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.Suspects;

/**
 * Suspect sounds
 * 
 * @author fidde
 * 
 */
public enum SuspectSounds implements Sounds {
	SCARLET("missScarlet.mp3", Suspects.MISS_SCARLET), MR_GREEN("mrGreen.mp3",
			Suspects.MR_GREEN), MISS_PEACH("missPeach.mp3", Suspects.MISS_PEACH), MRS_PEACOCK(
			"mrsPeacock.mp3", Suspects.MRS_PEACOCK), MRS_WHITE("mrsWhite.mp3",
			Suspects.MRS_WHITE), PROFESSOR_PLUM("professor.mp3",
			Suspects.PROFESSOR_PLUM), MADAME_ROSE("rose.mp3",
			Suspects.MADAME_ROSE), MUSTARD("mustard.mp3",
			Suspects.COLONEL_MUSTARD), SEARGENT_GRAY("seargentGray.mp3",
			Suspects.SERGEANT_GRAY), MONSIEUR_BRUNETTE("brunette.mp3",
			Suspects.MONSIEUR_BRUNETTE);

	private final URL URL;
	private final Card CARD;

	private SuspectSounds(String name, Card card) {
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
