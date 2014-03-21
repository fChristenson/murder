package se.fidde.murder.view.scenes;

/**
 * Screens class stores all screen id's and their fxml names
 * 
 * @author fidde
 * 
 */
public enum Screens {
	BOARD("board", "BoardScreen.fxml"), SUSPECTS("suspects",
			"SuspectSelectionScreen.fxml"), WEAPONS("weapons",
			"WeaponSelectionScreen.fxml"), ROOMS("rooms",
			"RoomSelectionScreen.fxml");

	public final String ID;
	public final String RESOURCE;

	private Screens(String id, String resource) {
		this.ID = id;
		this.RESOURCE = resource;
	}
}
