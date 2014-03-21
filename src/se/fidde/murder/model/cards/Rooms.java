package se.fidde.murder.model.cards;

/**
 * Room cards
 * 
 * @author fidde
 * 
 */
public enum Rooms implements Card {

	KITCHEN("Kitchen", 3, 0), BALLROOM("Ballroom", 0, 1), CONSERVATORY(
			"Conservatory", 1, 1), BILLJARD_ROOM("Billjard Room", 2, 1), LIBRARY(
			"Library", 3, 1), STUDY("Study", 0, 2), HALL("Hall", 0, 0), LOUNGE(
			"Lounge", 1, 0), DINING_ROOM("Dining Room", 2, 0);

	private final String NAME;
	private final int X;
	private final int Y;

	private Rooms(String name, int x, int y) {
		this.NAME = name;
		this.X = x;
		this.Y = y;
	}

	@Override
	public String getCardName() {
		return NAME;
	}

	@Override
	public int getColumn() {
		return X;
	}

	@Override
	public int getRow() {
		return Y;
	}
}
