package se.fidde.murder.model.cards;

/**
 * Empty card class to substitute null
 * 
 * @author fidde
 * 
 */
public enum EmptyCard implements Card {
	INSTANCE;

	@Override
	public String getCardName() {
		return "";
	}

	@Override
	public int getColumn() {
		return -1;
	}

	@Override
	public int getRow() {
		return -1;
	}

}
