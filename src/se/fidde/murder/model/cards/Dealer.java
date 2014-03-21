package se.fidde.murder.model.cards;

import java.util.ArrayList;
import java.util.List;

import se.fidde.murder.model.players.Player;
import se.fidde.murder.model.players.StandardPlayer;

/**
 * Dealer class for dealing cards to players and envelope
 * 
 * @author fidde
 * 
 */
public enum Dealer {

	INSTANCE;
	private final int NUM_CARDS;
	private final Player[] PLAYERS;
	private List<List<Card>> hands;
	private List<Card> cards;

	private Dealer() {
		this.hands = new ArrayList<>();
		this.PLAYERS = StandardPlayer.values();
		this.cards = Deck.INSTANCE.getCards();
		getCardsForEnvelope();
		this.NUM_CARDS = cards.size();
	}

	/**
	 * Deals card to players in provided list, if there is only 1 player no
	 * cards a dealt
	 * 
	 * @param players
	 *            list of players
	 */
	public void dealCards(List<Player> players) {
		if (players.size() < 2)
			return;

		int index = 0;
		for (Card card : cards) {
			players.get((index % players.size())).getCards().add(card);
			index++;
		}
	}

	private void getCardsForEnvelope() {
		for (Card card : cards) {
			if (card instanceof Suspects) {
				SecretEnvelope.MURDERER.setSuspect(card);
				cards.remove(card);
				break;
			}
		}

		for (Card card : cards) {
			if (card instanceof Weapons) {
				SecretEnvelope.MURDERER.setWeapon(card);
				cards.remove(card);
				break;
			}
		}

		for (Card card : cards) {
			if (card instanceof Rooms) {
				SecretEnvelope.MURDERER.setRoom(card);
				cards.remove(card);
				return;
			}
		}
	}

}
