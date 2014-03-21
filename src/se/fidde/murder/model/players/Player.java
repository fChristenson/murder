package se.fidde.murder.model.players;

import java.awt.Point;
import java.util.List;

import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.gridTiles.Doors;

/**
 * Player interface
 * 
 * @author fidde
 * 
 */
public interface Player {

	/**
	 * Get name
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Set name
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name);

	/**
	 * Gets cards
	 * 
	 * @return list of cards
	 */
	public List<Card> getCards();

	/**
	 * Sets cards
	 * 
	 * @param cards
	 *            list of cards
	 */
	public void setCards(List<Card> cards);

	/**
	 * Gets last door visited
	 * 
	 * @return Door
	 */
	public Doors getLastDoorVisited();

	/**
	 * Sets last door visited
	 * 
	 * @param door
	 *            Door
	 */
	public void setLastDoorVisited(Doors door);

	/**
	 * Gets token id
	 * 
	 * @return String
	 */
	public String getTokenId();

	/**
	 * Gets token startpoint
	 * 
	 * @return Point
	 */
	public Point getTokenStartPoint();
}
