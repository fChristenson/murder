package se.fidde.murder.model.players;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.gridTiles.Doors;

/**
 * Standard player class
 * 
 * @author fidde
 * 
 */
public enum StandardPlayer implements Player {

	PLAYER_1("Player 1", "player1Token", new Point(7, 24)), PLAYER_2(
			"Player 2", "player2Token", new Point(23, 6)), PLAYER_3("Player 3",
			"player3Token", new Point(14, 0)), PLAYER_4("Player 4",
			"player4Token", new Point(0, 17)), PLAYER_5("Player 5",
			"player5Token", new Point(9, 0)), PLAYER_6("Player 6",
			"player6Token", new Point(23, 19));

	private String name;
	private List<Card> cards;
	private Doors lastDoorVisited;
	private final String TOKEN_ID;
	private final Point TOKEN_START_POINT;

	private StandardPlayer(String name, String tokenId, Point point) {
		this.cards = new ArrayList<>();
		this.name = name;
		this.lastDoorVisited = Doors.EMPTY_DOOR;
		this.TOKEN_ID = tokenId;
		this.TOKEN_START_POINT = point;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Card> getCards() {
		return cards;
	}

	@Override
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public Doors getLastDoorVisited() {
		return lastDoorVisited;
	}

	@Override
	public void setLastDoorVisited(Doors door) {
		this.lastDoorVisited = door;
	}

	@Override
	public String getTokenId() {
		return TOKEN_ID;
	}

	@Override
	public Point getTokenStartPoint() {
		return TOKEN_START_POINT;
	}
}
