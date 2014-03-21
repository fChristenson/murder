package se.fidde.murder.model.gridTiles;

import java.awt.Point;

/**
 * Doors class for storing special tiles
 * 
 * @author fidde
 * 
 */
public enum Doors {

	KITCHEN_DOOR(new Point(4, 6)), BALLROOM_DOOR(new Point(9, 7)), BALLROOM_DOOR2(
			new Point(15, 5)), CONSERVATORY_DOOR(new Point(19, 5)), DINING_ROOM_DOOR(
			new Point(6, 15)), BILLJARD_ROOM_DOOR(new Point(18, 9)), LIBRARY_DOOR(
			new Point(17, 16)), LOUNGE_DOOR(new Point(6, 19)), HALL_DOOR(
			new Point(12, 18)), STUDY_DOOR(new Point(17, 21)), EMPTY_DOOR(
			new Point(-1, -1));

	private final Point point;

	private Doors(Point point) {
		this.point = point;
	}

	/**
	 * Checks if point is on a special tile
	 * 
	 * @param point
	 *            token position
	 * @return true if point == door, false otherwise
	 */
	public boolean isOnTile(Point point) {
		if (this.point.y == point.y && this.point.x == point.x)
			return true;

		return false;
	}
}
