package se.fidde.murder.model.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Secret envelope class, contains 3 cards to guess
 * 
 * @author fidde
 * 
 */
public enum SecretEnvelope {

	MURDERER;

	private Card suspect;
	private Card weapon;
	private Card room;
	private List<Card> cards = new ArrayList<>();

	/**
	 * Get suspect name
	 * 
	 * @return String
	 */
	public String getName() {
		return suspect.getCardName();
	}

	/**
	 * Sets suspect
	 * 
	 * @param suspect
	 *            Card
	 */
	public void setSuspect(Card suspect) {
		cards.add(suspect);
		this.suspect = suspect;
	}

	/**
	 * Gets weapon name
	 * 
	 * @return String
	 */
	public String getWeapon() {
		return weapon.getCardName();
	}

	/**
	 * Sets weapon
	 * 
	 * @param weapon
	 *            Card
	 */
	public void setWeapon(Card weapon) {
		cards.add(weapon);
		this.weapon = weapon;
	}

	/**
	 * Gets room name
	 * 
	 * @return String
	 */
	public String getRoom() {
		return room.getCardName();
	}

	/**
	 * Sets room
	 * 
	 * @param room
	 *            Card
	 */
	public void setRoom(Card room) {
		cards.add(room);
		this.room = room;
	}

	/**
	 * Gets all cards in class
	 * 
	 * @return List<Card>
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
		for (Card card : cards) {
			if (card instanceof Suspects)
				suspect = card;

			else if (card instanceof Weapons)
				weapon = card;

			else {
				room = card;
			}
		}
	}

}
