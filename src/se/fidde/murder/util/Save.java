package se.fidde.murder.util;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.players.Player;

/**
 * Save class for storing game state
 * 
 * @author fidde
 * 
 */
public class Save implements Serializable {

	private static final long serialVersionUID = -7011187892753013347L;
	private int stepsLeft;
	private boolean isFirstRoll;
	private boolean isAccuse;
	private Player playerInFocus;
	private List<Player> activePlayers;
	private boolean rollDisabled;
	private boolean accuseDisabled;
	private boolean suggestDisabled;
	private List<Card> envelope;
	private Map<Player, List<List<String>>> listViews;
	private Map<String, Point> tokenPositions;

	public Save(int stepsLeft, boolean isFirstRoll, boolean isAccuse,
			Player playerInFocus, List<Player> activePlayers,
			boolean rollDisabled, boolean suggestDisabled,
			boolean accuseDisabled, List<Card> envelope,
			Map<Player, List<List<String>>> listViews,
			Map<String, Point> tokenPositions) {

		this.stepsLeft = stepsLeft;
		this.isFirstRoll = isFirstRoll;
		this.isAccuse = isAccuse;
		this.playerInFocus = playerInFocus;
		this.activePlayers = activePlayers;
		this.rollDisabled = rollDisabled;
		this.suggestDisabled = suggestDisabled;
		this.accuseDisabled = accuseDisabled;
		this.setEnvelope(envelope);
		this.setListViews(listViews);
		this.setTokenPositions(tokenPositions);
	}

	/**
	 * Serializes object
	 * 
	 * @throws Exception
	 *             If save function fails
	 * @throws IOException
	 *             if closing stream fails
	 */
	public void save() throws Exception, IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(new File("save.ser"));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);

		} catch (Exception e) {
			throw new Exception("There was an error!");

		} finally {
			try {
				oos.close();

			} catch (IOException e) {
				throw new IOException("There was an error closing the stream");
			}
		}
	}

	/**
	 * Gets steps left
	 * 
	 * @return int
	 */
	public int getStepsLeft() {
		return stepsLeft;
	}

	/**
	 * Gets isFirstRoll
	 * 
	 * @return true or false
	 */
	public boolean isFirstRoll() {
		return isFirstRoll;
	}

	/**
	 * Gets isAccuse
	 * 
	 * @return true or false
	 */
	public boolean isAccuse() {
		return isAccuse;
	}

	/**
	 * Gets player in focus
	 * 
	 * @return Player
	 */
	public Player getPlayerInFocus() {
		return playerInFocus;
	}

	/**
	 * Gets active players
	 * 
	 * @return List of players
	 */
	public List<Player> getActivePlayers() {
		return activePlayers;
	}

	/**
	 * Gets isRollDisabled
	 * 
	 * @return true or false
	 */
	public boolean isRollDisabled() {
		return rollDisabled;
	}

	/**
	 * Gets isAccusedDisabled
	 * 
	 * @return true or false
	 */
	public boolean isAccuseDisabled() {
		return accuseDisabled;
	}

	/**
	 * Gets isSuggestDisabled
	 * 
	 * @return true or false
	 */
	public boolean isSuggestDisabled() {
		return suggestDisabled;
	}

	/**
	 * Gets envelope
	 * 
	 * @return list of cards
	 */
	public List<Card> getEnvelope() {
		return envelope;
	}

	/**
	 * Sets envelope
	 * 
	 * @param envelope
	 */
	public void setEnvelope(List<Card> envelope) {
		this.envelope = envelope;
	}

	/**
	 * Gets listView items
	 * 
	 * @return Map of player listviews as string lists
	 */
	public Map<Player, List<List<String>>> getListViewsNames() {
		return listViews;
	}

	/**
	 * Sets listViews
	 * 
	 * @param listViews
	 *            ListView
	 */
	public void setListViews(Map<Player, List<List<String>>> listViews) {
		this.listViews = listViews;
	}

	/**
	 * Gets token positions
	 * 
	 * @return List of points
	 */
	public Map<String, Point> getTokenPositions() {
		return tokenPositions;
	}

	/**
	 * Sets token positions
	 * 
	 * @param tokenPositions
	 *            Map with token id and point
	 */
	public void setTokenPositions(Map<String, Point> tokenPositions) {
		this.tokenPositions = tokenPositions;
	}

}
