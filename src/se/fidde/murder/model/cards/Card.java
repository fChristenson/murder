package se.fidde.murder.model.cards;

public interface Card {

	/**
	 * Gets card name
	 * 
	 * @return string
	 */
	public String getCardName();

	/**
	 * Gets card column index
	 * 
	 * @return
	 */
	public int getColumn();

	/**
	 * Gets card row index
	 * 
	 * @return
	 */
	public int getRow();
}
