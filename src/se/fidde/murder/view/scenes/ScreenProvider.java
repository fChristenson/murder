package se.fidde.murder.view.scenes;

import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import se.fidde.murder.controllers.ScreenController;
import se.fidde.murder.util.Tools;

/**
 * Provider class stores references to all screens
 * 
 * @author fidde
 * 
 */
public class ScreenProvider extends StackPane {

	private HashMap<String, Node> screens = new HashMap<>();

	public ScreenProvider() {
		super();
	}

	/**
	 * Add screen
	 * 
	 * @param name
	 *            name of screen
	 * @param scene
	 *            Node scene
	 */
	public void addScreen(String name, Node scene) {
		screens.put(name, scene);
	}

	/**
	 * Gets screen
	 * 
	 * @param name
	 *            id of screen
	 * @return Node
	 */
	public Node getScreen(String name) {
		return screens.get(name);
	}

	/**
	 * Gets fxml doc and loads it
	 * 
	 * @param name
	 *            id to map root to
	 * @param resource
	 *            path to fxml doc
	 */
	public void loadScreen(String name, String resource) {
		try {
			FXMLLoader loader = new FXMLLoader(
					ScreenProvider.class.getResource(resource),
					ResourceBundle.getBundle("se.fidde.murder.model.lang."
							+ Tools.getLanguage()));
			Parent screen = (Parent) loader.load();
			ScreenController screenController = loader.getController();
			screenController.setParentController(this);
			addScreen(name, screen);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets screen
	 * 
	 * @param name
	 *            id of screen
	 */
	public void setScreen(String name) {
		if (getChildren().isEmpty())
			getChildren().add(getScreen(name));

		else {
			getChildren().remove(0);
			getChildren().add(getScreen(name));
		}
	}

	/**
	 * Unloads screen
	 * 
	 * @param name
	 *            id of screen
	 */
	public void unloadScreen(String name) {
		screens.remove(name);
	}

}
