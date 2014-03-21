package se.fidde.murder.model.audio.rooms;

import java.net.URL;

import se.fidde.murder.model.audio.Sounds;
import se.fidde.murder.model.cards.Card;
import se.fidde.murder.model.cards.Rooms;

/**
 * Room sounds
 * 
 * @author fidde
 * 
 */
public enum RoomSounds implements Sounds {
	HALL("hall.mp3", Rooms.HALL), LOUNGE("lounge.mp3", Rooms.LOUNGE), DINING_ROOM(
			"diningRoom.mp3", Rooms.DINING_ROOM), KITCHEN("kitchen.mp3",
			Rooms.KITCHEN), BALLROOM("ballRoom.mp3", Rooms.BALLROOM), CONSERVATORY(
			"conservatory.mp3", Rooms.CONSERVATORY), BILLJARD_ROOM(
			"billjardRoom.mp3", Rooms.BILLJARD_ROOM), LIBRARY("library.mp3",
			Rooms.LIBRARY), STUDY("study.mp3", Rooms.STUDY);

	private final URL URL;
	private final Card CARD;

	private RoomSounds(String name, Card card) {
		this.URL = getClass().getResource(name);
		this.CARD = card;
	}

	@Override
	public String getUrl() {
		return URL.toString();
	}

	@Override
	public Card getCARD() {
		return CARD;
	}

}
