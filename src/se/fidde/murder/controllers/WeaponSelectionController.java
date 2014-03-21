package se.fidde.murder.controllers;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import se.fidde.murder.model.audio.Sounds;
import se.fidde.murder.model.audio.weapons.WeaponSounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.EmptyCard;
import se.fidde.murder.model.cards.Weapons;
import se.fidde.murder.util.Suggestion;
import se.fidde.murder.view.scenes.ScreenProvider;
import se.fidde.murder.view.scenes.Screens;

/**
 * Weapon screen controller
 * 
 * @author fidde
 * 
 */
public class WeaponSelectionController implements Initializable,
		ScreenController {

	@FXML
	Button selectButton;

	@FXML
	BorderPane borderPane;

	@FXML
	Label cardName;

	private final double CARD_WIDTH = 179.0;
	private final double CARD_HEIGHT = 257.0;
	private ScreenProvider myProvider;
	private Card weapon;
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
	 * Gets the selected card
	 * 
	 * @param event
	 *            MouseEvent
	 */
	@FXML
	public void getSelectionValue(MouseEvent event) {
		int x = (int) Math.floor((event.getX() / CARD_WIDTH));
		int y = (int) Math.floor((event.getY() / CARD_HEIGHT));
		Point point = new Point(x, y);
		weapon = getCard(point);
		cardName.setText(weapon.getCardName());

		if (weapon instanceof EmptyCard)
			return;

		playSoundOf(weapon);
		selectButton.setDisable(false);
	}

	/**
	 * Commits player selection
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void setSelectedValue(ActionEvent event) {
		if (!cardName.getText().equals(defaultCardName)) {
			if (clip != null)
				clip.stop();

			Suggestion.INSTANCE.getCards().add(weapon);
			myProvider.setScreen(Screens.ROOMS.ID);
			cardName.setText(defaultCardName);
		}
	}

	private Card getCard(Point point) {
		for (Card card : Weapons.values()) {
			if (point.x == card.getColumn() && point.y == card.getRow())
				return card;
		}
		return EmptyCard.INSTANCE;
	}

	private void playSoundOf(Card card) {
		for (Sounds sound : WeaponSounds.values()) {
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
	 * Sets controller for this screen
	 */
	@Override
	public void setParentController(ScreenProvider controller) {
		myProvider = controller;
	}
}
