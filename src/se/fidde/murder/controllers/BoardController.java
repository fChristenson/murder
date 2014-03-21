package se.fidde.murder.controllers;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import se.fidde.murder.model.audio.board.BoardSounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.Dealer;
import se.fidde.murder.model.cards.Rooms;
import se.fidde.murder.model.cards.SecretEnvelope;
import se.fidde.murder.model.cards.Suspects;
import se.fidde.murder.model.cards.Weapons;
import se.fidde.murder.model.gridTiles.Directions;
import se.fidde.murder.model.gridTiles.Doors;
import se.fidde.murder.model.gridTiles.InnerWalls;
import se.fidde.murder.model.gridTiles.OuterWalls;
import se.fidde.murder.model.players.Player;
import se.fidde.murder.model.players.StandardPlayer;
import se.fidde.murder.util.Save;
import se.fidde.murder.util.TokenId;
import se.fidde.murder.util.Tools;
import se.fidde.murder.view.scenes.ScreenProvider;
import se.fidde.murder.view.scenes.Screens;

import com.sun.javafx.collections.ObservableListWrapper;

/**
 * Board controller class
 * 
 * @author fidde
 * 
 */
public class BoardController implements Initializable, ScreenController {

	/**
	 * Reference to rollbutton node
	 */
	@FXML
	public static Button roll;

	/**
	 * Reference to accusebutton node
	 */
	@FXML
	public static Button accuse;

	/**
	 * Reference to suggestbutton node
	 */
	@FXML
	public static Button suggest;

	@FXML
	ImageView playerPicture;

	@FXML
	Label stepsLeftCounter;

	@FXML
	Accordion accordion;

	@FXML
	GridPane boardGrid;

	@FXML
	ChoiceBox<String> choiceBoxNumPlayers;

	private static int stepsLeft = 0;
	private static int num_players;
	private static final MediaPlayer THEME = new MediaPlayer(new Media(
			BoardSounds.THEME.getUrl()));
	private boolean isFirstRoll = true;
	private ScreenProvider myProvider;
	private AudioClip clip;
	private boolean gameWasLoaded = false;
	public static boolean isAccuse;
	public static Map<Player, List<ListView<String>>> playerListViews = new HashMap<>();
	public static Player playerInFocus;
	public static Map<String, Circle> tokens = new HashMap<String, Circle>();
	public static List<Player> activePlayers;
	public static Iterator<Player> iterator;

	/**
	 * Initializes the gameboard
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createListViewMap();
		addCardsToListViews();
		playerInFocus = StandardPlayer.PLAYER_1;
		num_players = 0;
		createTokenMap();
		startMusic();
	}

	private void createTokenMap() {
		for (TokenId id : TokenId.values())
			tokens.put(id.toString(), (Circle) boardGrid.lookup("#" + id));
	}

	private void addCardsToListViews() {
		for (List<ListView<String>> list : playerListViews.values()) {
			list.get(0).setItems(toObservableListWrapper(Suspects.values()));
			list.get(1).setItems(toObservableListWrapper(Weapons.values()));
			list.get(2).setItems(toObservableListWrapper(Rooms.values()));
		}
	}

	private void createListOfActivePlayers() {
		if (activePlayers != null)
			return;

		activePlayers = new LinkedList<>();
		for (int i = 0; i < num_players; i++)
			activePlayers.add(StandardPlayer.values()[i]);

		iterator = activePlayers.listIterator();
		playerInFocus = iterator.next();
	}

	private void createListViewMap() {
		VBox vBox = null;
		int index = 0;
		for (TitledPane pane : accordion.getPanes()) {
			AnchorPane anchor = (AnchorPane) pane.getContent();
			vBox = (VBox) anchor.getChildren().get(0);
			List<ListView<String>> list = new ArrayList<>();
			createListOfListviews(list, vBox);
			playerListViews.put(StandardPlayer.values()[index], list);
			index++;
		}
	}

	private void removeCardsOnHandFromListViews() {
		for (Player player : StandardPlayer.values()) {
			if (playerListViews.containsKey(player)) {
				for (ListView<String> listView : playerListViews.get(player)) {
					for (Iterator<String> iter = listView.getItems()
							.listIterator(); iter.hasNext() != false;) {

						if (hasCard(iter.next(), player)) {
							iter.remove();
						}
					}
				}
			}
		}
	}

	private boolean hasCard(String name, Player player) {
		for (Card card : player.getCards()) {
			if (card.getCardName().equals(name))
				return true;
		}
		return false;
	}

	private void createListOfListviews(List<ListView<String>> listToCreate,
			VBox box) {
		for (Node VBox : box.getChildren()) {
			VBox child = (VBox) VBox;

			for (Node list : child.getChildren()) {
				if (list instanceof ListView<?>) {
					ListView<String> listToAdd = (ListView<String>) list;
					listToCreate.add(listToAdd);
				}
			}
		}
	}

	private ObservableListWrapper<String> toObservableListWrapper(Card[] array) {
		List<String> list = new ArrayList<>();

		for (Card key : array)
			list.add(key.getCardName());

		return new ObservableListWrapper<>(list);
	}

	/**
	 * Gets the themesong
	 * 
	 * @return enum instance of the themesong
	 */
	public static MediaPlayer getTheme() {
		return THEME;
	}

	/**
	 * Rolls the dice and updates game state accordingly
	 */
	@FXML
	public void rollDice() {
		stepsLeft = Tools.rollT6();
		updateStepsLeftLabel();
		roll.setDisable(true);
		disableSuggestAndAccuseButtons(true);
		new AudioClip(BoardSounds.DICE.getUrl()).play();
		choiceBoxNumPlayers.setDisable(true);
		setNumPlayersAndUpdateAccordion();
		createListOfActivePlayers();
		dealCards();

		if (!isFirstRoll) {
			switchActivePlayer();

			ObservableList<TitledPane> panes = accordion.getPanes();
			for (TitledPane pane : panes) {
				if (pane.getId().equals(playerInFocus.getName()))
					pane.setExpanded(true);
			}
		}

		isFirstRoll = false;
	}

	private void dealCards() {
		if (!isFirstRoll || gameWasLoaded)
			return;

		Dealer.INSTANCE.dealCards(activePlayers);
		removeCardsOnHandFromListViews();
	}

	private void setNumPlayersAndUpdateAccordion() {
		if (num_players > 0)
			return;

		try {
			num_players = Integer.parseInt(choiceBoxNumPlayers.getValue());

		} catch (NumberFormatException e) {
			num_players = 1;
		}

		for (int i = num_players; i < accordion.getPanes().size(); i++) {
			accordion.getPanes().get(i).setDisable(true);
			accordion.getPanes().get(i).setVisible(false);
		}
	}

	private void disableSuggestAndAccuseButtons(boolean bool) {
		accuse.setDisable(bool);
		suggest.setDisable(bool);
	}

	private void updateStepsLeftLabel() {
		stepsLeftCounter.setText(String.valueOf(stepsLeft));
	}

	/**
	 * Opens suspect screen
	 */
	@FXML
	public void suggest() {
		if (clip != null)
			clip.stop();

		disableSuggestAndAccuseButtons(true);
		myProvider.setScreen(Screens.SUSPECTS.ID);
	}

	/**
	 * Opens suspect screen in accuse mode
	 */
	@FXML
	public void accuse() {
		if (clip != null)
			clip.stop();
		disableSuggestAndAccuseButtons(true);
		isAccuse = true;
		myProvider.setScreen(Screens.SUSPECTS.ID);
	}

	/**
	 * Moves the currently selected token
	 * 
	 * @param event
	 *            KeyEvent
	 */
	@FXML
	public void move(KeyEvent event) {
		if (stepsLeft < 1)
			return;

		int col = GridPane
				.getColumnIndex(tokens.get(playerInFocus.getTokenId()));
		int row = GridPane.getRowIndex(tokens.get(playerInFocus.getTokenId()));
		Point start = new Point(col, row);

		switch (event.getCode()) {
		case W:
			moveToken(start, Directions.NORTH);
			break;

		case S:
			moveToken(start, Directions.SOUTH);
			break;

		case A:
			moveToken(start, Directions.WEST);
			break;

		case D:
			moveToken(start, Directions.EAST);
			break;

		default:
			return;
		}

	}

	private void moveToken(Point point, Directions direction) {
		if (!canMoveToken(point, direction))
			return;

		switch (direction) {
		case NORTH:
			GridPane.setRowIndex(tokens.get(playerInFocus.getTokenId()),
					point.y - 1);
			break;

		case EAST:
			GridPane.setColumnIndex(tokens.get(playerInFocus.getTokenId()),
					point.x + 1);
			break;

		case SOUTH:
			GridPane.setRowIndex(tokens.get(playerInFocus.getTokenId()),
					point.y + 1);
			break;

		case WEST:
			GridPane.setColumnIndex(tokens.get(playerInFocus.getTokenId()),
					point.x - 1);
			break;

		default:
			throw new AssertionError(direction + " is not a valid direction!");
		}
		updateStepsLeftAndCheckForDoors();
		if (stepsLeft < 1)
			roll.setDisable(false);

	}

	private void updateStepsLeftAndCheckForDoors() {
		stepsLeft--;
		updateStepsLeftLabel();
		checkForDoor();
	}

	private void checkForDoor() {
		if (isLastDoorVisited()) {
			playSameRoomClip();

		} else {
			specialTileFunctions();
		}
	}

	private void playSameRoomClip() {
		if (clip == null) {
			clip = new AudioClip(BoardSounds.SAME_ROOM.getUrl());
			clip.play();

		} else {
			clip.stop();
			clip = new AudioClip(BoardSounds.SAME_ROOM.getUrl());
			clip.play();
		}
	}

	private boolean isLastDoorVisited() {
		int x = GridPane.getColumnIndex(tokens.get(playerInFocus.getTokenId()));
		int y = GridPane.getRowIndex(tokens.get(playerInFocus.getTokenId()));

		if (playerInFocus.getLastDoorVisited().isOnTile(new Point(x, y)))
			return true;

		return false;
	}

	private boolean canMoveToken(Point start, Directions direction) {
		Point temp = new Point(start.x, start.y);
		int x = start.x;
		int y = start.y;

		switch (direction) {
		case NORTH:
			temp.move(x, y - 1);
			break;

		case EAST:
			temp.move(x + 1, y);
			break;

		case SOUTH:
			temp.move(x, y + 1);
			break;

		case WEST:
			temp.move(x - 1, y);
			break;

		default:
			throw new AssertionError(direction + " is not a valid direction!");
		}

		if (OuterWalls.INSTANCE.isOuterWall(temp)
				|| InnerWalls.INSTANCE.isInnerWall(temp))
			return false;

		return true;
	}

	private void specialTileFunctions() {
		int col = GridPane
				.getColumnIndex(tokens.get(playerInFocus.getTokenId()));
		int row = GridPane.getRowIndex(tokens.get(playerInFocus.getTokenId()));

		for (Doors tile : Doors.values()) {
			if (tile.isOnTile(new Point(col, row))) {
				playEnterRoomClip();
				disableSuggestAndAccuseButtons(false);
				stepsLeft = 0;
				updateStepsLeftLabel();
				playerInFocus.setLastDoorVisited(tile);
				return;
			}
		}
	}

	private void playEnterRoomClip() {
		if (clip == null) {
			clip = new AudioClip(BoardSounds.ENTER_ROOM.getUrl());
			clip.play();

		} else {
			clip.stop();
			clip = new AudioClip(BoardSounds.ENTER_ROOM.getUrl());
			clip.play();
		}
	}

	private void switchActivePlayer() {
		try {
			playerInFocus = iterator.next();

		} catch (NoSuchElementException e) {
			iterator = activePlayers.listIterator();
			playerInFocus = iterator.next();

		}
	}

	private void startMusic() {
		THEME.setCycleCount(MediaPlayer.INDEFINITE);
		THEME.setVolume(0.2);
		THEME.play();
	}

	/**
	 * Sets parent controller class for use in screen transition API
	 */
	@Override
	public void setParentController(ScreenProvider controller) {
		myProvider = controller;
	}

	/**
	 * Saves the gamestate
	 */
	@FXML
	public void save() {
		if (isFirstRoll)
			return;

		Map<String, Point> tokenPositions = new HashMap<>();
		for (Entry<String, Circle> entry : tokens.entrySet()) {
			tokenPositions.put(entry.getKey(),
					new Point(GridPane.getColumnIndex(entry.getValue()),
							GridPane.getRowIndex(entry.getValue())));
		}

		Save gameSave = new Save(stepsLeft, isFirstRoll, isAccuse,
				playerInFocus, activePlayers, roll.isDisabled(),
				suggest.isDisabled(), accuse.isDisabled(),
				SecretEnvelope.MURDERER.getCards(), convertListViewsToLists(),
				tokenPositions);

		try {
			gameSave.save();
		} catch (Exception e) {
			Tools.printFileError();
		}
	}

	private Map<Player, List<List<String>>> convertListViewsToLists() {
		Map<Player, List<List<String>>> result = new HashMap<>();

		for (Player player : activePlayers) {
			List<List<String>> listToSave = new ArrayList<>();
			List<ListView<String>> list = playerListViews.get(player);

			if (list != null) {
				for (ListView<String> listView : list) {
					listToSave.add(Tools.toList(listView));
				}
			}
			result.put(player, listToSave);
		}

		return result;
	}

	/**
	 * Loads a saved game state
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void load(ActionEvent event) {
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		Save loadedSave;
		try {
			fis = new FileInputStream("save.ser");
			ois = new ObjectInputStream(fis);
			loadedSave = (Save) ois.readObject();
			stepsLeft = loadedSave.getStepsLeft();
			updateStepsLeftLabel();
			num_players = loadedSave.getActivePlayers().size();
			isFirstRoll = loadedSave.isFirstRoll();
			isAccuse = loadedSave.isAccuse();
			playerInFocus = loadedSave.getPlayerInFocus();
			activePlayers = loadedSave.getActivePlayers();
			updateListViews(loadedSave);
			roll.setDisable(loadedSave.isRollDisabled());
			suggest.setDisable(loadedSave.isSuggestDisabled());
			accuse.setDisable(loadedSave.isAccuseDisabled());
			SecretEnvelope.MURDERER.setCards(loadedSave.getEnvelope());
			choiceBoxNumPlayers.setDisable(true);
			updateTokenPositions(loadedSave);
			updatePanes();
			gameWasLoaded = true;
			iterator = activePlayers.listIterator();

		} catch (Exception e) {
			Tools.printFileError();
		}

	}

	private void updatePanes() {
		for (int i = 0; i < accordion.getPanes().size(); i++) {
			if (i < activePlayers.size()) {
				accordion.getPanes().get(i).setVisible(true);
				accordion.getPanes().get(i).setDisable(false);

			} else {
				accordion.getPanes().get(i).setVisible(false);
				accordion.getPanes().get(i).setDisable(true);
			}

		}
	}

	private void updateTokenPositions(Save loadedSave) {
		Map<String, Point> points = loadedSave.getTokenPositions();

		for (Entry<String, Point> entry : points.entrySet()) {
			GridPane.setColumnIndex(boardGrid.lookup("#" + entry.getKey()),
					entry.getValue().x);
			GridPane.setRowIndex(boardGrid.lookup("#" + entry.getKey()),
					entry.getValue().y);
		}
	}

	private void updateListViews(Save loadedSave) {
		Map<Player, List<List<String>>> names = loadedSave.getListViewsNames();
		for (Player player : activePlayers) {
			List<ListView<String>> listToUpdate = playerListViews.get(player);

			for (int i = 0; i < listToUpdate.size(); i++) {
				ObservableListWrapper<String> list = new ObservableListWrapper<>(
						names.get(player).get(i));
				listToUpdate.get(i).setItems(list);
			}
		}
	}

	/**
	 * Resizes picture and displays it on screen
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void changePicture(ActionEvent event) {
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(null);

				if (file == null)
					cancel();

				else if (!file.getName().matches(
						"^.+(jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG)$")) {

					Tools.printFileError();
					cancel();
				}
				try {
					BufferedImage test = null;
					test = ImageIO.read(file);
					test = Scalr.resize(test, 200, 200);
					playerPicture.setImage(SwingFXUtils.toFXImage(test, null));
				} catch (Exception e) {
					Tools.printFileError();

				}
				return true;
			}
		};
		task.run();
	}

	/**
	 * Resets the state of the game
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void restart(ActionEvent event) {
		if (isFirstRoll)
			return;

		placeTokens();
		isFirstRoll = true;
		activePlayers = null;
		choiceBoxNumPlayers.setDisable(false);
		roll.setDisable(false);
		accuse.setDisable(true);
		suggest.setDisable(true);
		accordion.getPanes().get(0).setExpanded(true);
		setPanesToVisibleAndEnabled();
		createListViewMap();
		addCardsToListViews();
		playerInFocus = StandardPlayer.PLAYER_1;
		stepsLeft = 0;
		updateStepsLeftLabel();
		createTokenMap();
		num_players = 0;
	}

	private void placeTokens() {
		for (Player player : StandardPlayer.values()) {
			Node circle = boardGrid.lookup("#" + player.getTokenId());
			if (circle != null) {
				circle.setVisible(true);
				GridPane.setColumnIndex(circle, player.getTokenStartPoint().x);
				GridPane.setRowIndex(circle, player.getTokenStartPoint().y);
			}
		}
	}

	private void setPanesToVisibleAndEnabled() {
		for (TitledPane pane : accordion.getPanes()) {
			pane.setVisible(true);
			pane.setDisable(false);
		}
	}

	/**
	 * Exits the application
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void exit(ActionEvent event) {
		System.exit(0);
	}

}
