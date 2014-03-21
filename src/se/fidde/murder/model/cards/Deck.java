package se.fidde.murder.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck class to hold all cards
 * 
 * @author fidde
 * 
 */
public enum Deck {

	INSTANCE;

	private final List<Card> CARDS;

	private Deck() {
		CARDS = new ArrayList<>();

		for (Card card : Suspects.values())
			CARDS.add(card);

		for (Card card : Weapons.values())
			CARDS.add(card);

		for (Card card : Rooms.values())
			CARDS.add(card);

		Collections.shuffle(CARDS);
	}

	/**
	 * Gets cards
	 * 
	 * @return list of cards
	 */
	public List<Card> getCards() {
		return new ArrayList<>(CARDS);
	}

}
