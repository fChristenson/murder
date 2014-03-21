package se.fidde.murder.controllers;

import java.awt.Point;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;

import javax.swing.JOptionPane;

import se.fidde.murder.model.audio.Sounds;
import se.fidde.murder.model.audio.board.BoardSounds;
import se.fidde.murder.model.audio.rooms.RoomSounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.EmptyCard;
import se.fidde.murder.model.cards.Rooms;
import se.fidde.murder.model.cards.SecretEnvelope;
import se.fidde.murder.util.Suggestion;
import se.fidde.murder.util.Tools;
import se.fidde.murder.view.scenes.ScreenProvider;
import se.fidde.murder.view.scenes.Screens;

/**
 * Room selection screen controller
 * 
 * @author fidde
 * 
 */
public class RoomSelectionController implements Initializable, ScreenController {

	@FXML
	Button selectButton;

	@FXML
	BorderPane borderPane;

	@FXML
	Label cardName;

	private final double CARD_WIDTH = 158.0;
	private final double CARD_HEIGHT = 214.0;
	private ScreenProvider myProvider;
	private Card room;
	private AudioClip clip;
	private String defaultCardName;

	/**
	 * Initializes the screen
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		defaultCardName = cardName.getText();
		selectButton.setDisable(true);
	}

	/**
	 * Gets the selected card name
	 * 
	 * @param event
	 *            MouseEvent
	 */
	@FXML
	public void getSelectionValue(MouseEvent event) {
		int x = (int) Math.floor((event.getX() / CARD_WIDTH));
		int y = (int) Math.floor((event.getY() / CARD_HEIGHT));
		Point point = new Point(x, y);
		room = getCard(point);

		if (room instanceof EmptyCard)
			return;

		cardName.setText(room.getCardName());
		playSoundOf(room);
		selectButton.setDisable(false);

	}

	/**
	 * Commits the users choice of card and updates gamestate
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void setSelectedValue(ActionEvent event) {
		if (!cardName.getText().equals(defaultCardName)) {

			if (clip != null)
				clip.stop();

			Suggestion.INSTANCE.getCards().add(room);
			myProvider.setScreen(Screens.BOARD.ID);
			cardName.setText(defaultCardName);
			List<ListView<String>> list = BoardController.playerListViews
					.get(BoardController.playerInFocus);
			updateListView(list, Suggestion.INSTANCE.getCards());

			if (BoardController.isAccuse && !isWinner()) {
				executeLoserFunctions();

			} else if (BoardController.isAccuse && isWinner()) {
				playerWinnerClip();

				JOptionPane.showMessageDialog(
						null,
						ResourceBundle.getBundle(
								"se.fidde.murder.model.lang."
										+ Tools.getLanguage()).getObject(
								"Winner"),
						ResourceBundle
								.getBundle(
										"se.fidde.murder.model.lang."
												+ Tools.getLanguage())
								.getObject("Winner").toString(),
						JOptionPane.INFORMATION_MESSAGE);
				BoardController.getTheme().stop();
				System.exit(0);
			}
			BoardController.isAccuse = false;

		}
	}

	private void playerWinnerClip() {
		if (clip == null) {
			clip = new AudioClip(BoardSounds.MURDERER_FOUND.getUrl());
			clip.setVolume(0.5);
			clip.play();

		} else {
			clip.stop();
			clip = new AudioClip(BoardSounds.MURDERER_FOUND.getUrl());
			clip.setVolume(0.5);
			clip.play();
		}
	}

	private void executeLoserFunctions() {
		playLoserClip();
		BoardController.tokens.get(BoardController.playerInFocus.getTokenId())
				.setVisible(false);
		BoardController.tokens.remove(BoardController.playerInFocus
				.getTokenId());

		if (BoardController.activePlayers.size() < 2) {
			BoardController.roll.setDisable(true);
			BoardController.accuse.setDisable(true);
			BoardController.suggest.setDisable(true);
			playLoserClip();

			JOptionPane.showMessageDialog(
					null,
					ResourceBundle.getBundle(
							"se.fidde.murder.model.lang.sv"
									+ Tools.getLanguage()).getObject("Loser"),
					ResourceBundle
							.getBundle(
									"se.fidde.murder.model.lang."
											+ Tools.getLanguage())
							.getObject("Loser").toString(),
					JOptionPane.INFORMATION_MESSAGE);

		} else {
			try {
				BoardController.iterator.remove();

			} catch (NoSuchElementException e) {
				BoardController.iterator = BoardController.activePlayers
						.listIterator();
			}

		}

	}

	private void playLoserClip() {
		if (clip == null) {
			clip = new AudioClip(BoardSounds.LOSER.getUrl());
			clip.play();
		} else {
			clip.stop();
			clip = new AudioClip(BoardSounds.LOSER.getUrl());
			clip.play();
		}
	}

	private boolean isWinner() {
		int cardsFound = 0;
		for (Card card : Suggestion.INSTANCE.getCards()) {
			if (card.getCardName().equals(SecretEnvelope.MURDERER.getName()))
				cardsFound++;
		}

		if (cardsFound < 1)
			return false;

		for (Card card : Suggestion.INSTANCE.getCards()) {
			if (card.getCardName().equals(SecretEnvelope.MURDERER.getWeapon()))
				cardsFound++;
		}

		if (cardsFound < 2)
			return false;

		for (Card card : Suggestion.INSTANCE.getCards()) {
			if (card.getCardName().equals(SecretEnvelope.MURDERER.getRoom()))
				cardsFound++;
		}

		if (cardsFound < 3)
			return false;

		return true;
	}

	private Card getCard(Point point) {
		for (Card card : Rooms.values()) {
			if (point.x == card.getColumn() && point.y == card.getRow())
				return card;
		}
		return EmptyCard.INSTANCE;
	}

	private void playSoundOf(Card card) {
		for (Sounds sound : RoomSounds.values()) {
			if (sound.getCARD() == card) {
				if (clip != null) {
					clip.stop();
					clip = new AudioClip(sound.getUrl());
					clip.play();

				} else {
					clip = new AudioClip(sound.getUrl());
					clip.play();
				}
			}
		}
	}

	/**
	 * Sets parent controller
	 */
	@Override
	public void setParentController(ScreenProvider controller) {
		myProvider = controller;
	}

	private void updateListView(List<ListView<String>> listViewList,
			List<Card> cards) {
		for (Card card : cards) {
			if (!suggestIsCorrect(card)) {
				for (ListView<String> list : listViewList)
					removeSuggest(list, card);
			}

		}
	}

	private boolean suggestIsCorrect(Card card) {
		if (card.getCardName().equals(SecretEnvelope.MURDERER.getName()))
			return true;

		else if (card.getCardName().equals(SecretEnvelope.MURDERER.getWeapon()))
			return true;

		else if (card.getCardName().equals(SecretEnvelope.MURDERER.getRoom()))
			return true;

		return false;
	}

	private boolean removeSuggest(ListView<String> listview, Card suggest) {
		for (String name : listview.getItems()) {
			if (name.equals(suggest.getCardName())) {
				listview.getItems().remove(name);
				return true;
			}
		}
		return false;
	}
}
