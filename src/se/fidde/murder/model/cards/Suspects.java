package se.fidde.murder.model.cards;

/**
 * Suspect class
 * 
 * @author fidde
 * 
 */
public enum Suspects implements Card {

	MISS_SCARLET("Miss Scarlet", 0, 0), COLONEL_MUSTARD("Colonel Mustard", 1, 0), MRS_WHITE(
			"Mrs White", 2, 0), MISS_PEACH("Miss Peach", 3, 0), SERGEANT_GRAY(
			"Seargent Gray", 4, 0), MR_GREEN("Mr Green", 0, 1), MRS_PEACOCK(
			"Mrs Peacock", 1, 1), PROFESSOR_PLUM("Professor Plum", 2, 1), MONSIEUR_BRUNETTE(
			"Monsieur Brunette", 3, 1), MADAME_ROSE("Madame Rose", 4, 1);

	private final String NAME;
	private final int X;
	private final int Y;

	private Suspects(String name, int x, int y) {
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
