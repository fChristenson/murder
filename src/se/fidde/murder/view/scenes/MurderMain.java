package se.fidde.murder.view.scenes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Main application window class
 * 
 * @author fidde
 * 
 */
public class MurderMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		ScreenProvider provider = new ScreenProvider();
		provider.loadScreen(Screens.BOARD.ID, Screens.BOARD.RESOURCE);
		provider.loadScreen(Screens.SUSPECTS.ID, Screens.SUSPECTS.RESOURCE);
		provider.loadScreen(Screens.WEAPONS.ID, Screens.WEAPONS.RESOURCE);
		provider.loadScreen(Screens.ROOMS.ID, Screens.ROOMS.RESOURCE);
		provider.setScreen(Screens.BOARD.ID);

		Scene scene = new Scene(provider);
		scene.setFill(Paint.valueOf("black"));
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(1080);
		primaryStage.setMinHeight(700);
		primaryStage.setWidth(1080);
		primaryStage.setHeight(700);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
