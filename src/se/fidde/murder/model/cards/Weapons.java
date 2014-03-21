package se.fidde.murder.model.cards;

/**
 * Weapons class
 * 
 * @author fidde
 * 
 */
public enum Weapons implements Card {

	KNIFE("Knife", 0, 0), AXE("Axe", 1, 0), WRENCH("Wrench", 2, 0), ROPE(
			"Rope", 3, 0), BOMB("Bomb", 4, 0), POISON("Poison", 0, 1), REVOLVER(
			"Revolver", 1, 1), LEAD_PIPE("Lead Pipe", 2, 1), CANDLESTICK(
			"Candlestick", 3, 1), SYRINGE("Syringe", 4, 1);

	private final String NAME;
	private final int X;
	private final int Y;

	private Weapons(String name, int x, int y) {
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
