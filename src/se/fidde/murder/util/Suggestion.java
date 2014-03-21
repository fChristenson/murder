package se.fidde.murder.util;

import java.util.ArrayList;
import java.util.List;

import se.fidde.murder.model.cards.Card;

/**
 * Suggestion class stores player guesses
 * 
 * @author fidde
 * 
 */
public enum Suggestion {
	INSTANCE;

	private List<Card> cards;

	private Suggestion() {
		cards = new ArrayList<>();
	}

	/**
	 * Gets cards
	 * 
	 * @return list of cards
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Sets cards
	 * 
	 * @param cards
	 *            list of cards
	 */
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}
