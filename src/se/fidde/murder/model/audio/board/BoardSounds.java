package se.fidde.murder.model.audio.board;

import java.net.URL;

import se.fidde.murder.model.audio.Sounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.EmptyCard;

/**
 * Board sounds
 * 
 * @author fidde
 * 
 */
public enum BoardSounds implements Sounds {

	THEME("murderTheme.mp3", EmptyCard.INSTANCE), DICE("diceThrow.mp3",
			EmptyCard.INSTANCE), MURDERER_FOUND("murdererFound.mp3",
			EmptyCard.INSTANCE), ENTER_ROOM("enterRoom.mp3", EmptyCard.INSTANCE), SAME_ROOM(
			"enterSameRoom.mp3", EmptyCard.INSTANCE), LOSER("loser.mp3",
			EmptyCard.INSTANCE);

	private final URL URL;
	private final Card CARD;

	private BoardSounds(String name, Card card) {
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
