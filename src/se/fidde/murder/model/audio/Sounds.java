package se.fidde.murder.model.audio;

import se.fidde.murder.model.cards.Card;

/**
 * Audio inteface
 * 
 * @author fidde
 * 
 */
public interface Sounds {

	/**
	 * Gets sound url as a string
	 * 
	 * @return string
	 */
	public String getUrl();

	/**
	 * Gets associated card
	 * 
	 * @return Card
	 */
	public Card getCARD();

}