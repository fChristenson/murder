package se.fidde.murder.model.gridTiles;

import java.awt.Point;

/**
 * Outer wall class, stores the edge walls on board
 * 
 * @author fidde
 * 
 */
public enum OuterWalls {
	INSTANCE;

	private final int LAST_COLUM_INDEX = 23;
	private final int LAST_ROW_INDEX = 24;

	/**
	 * Checks if point is an outer wall
	 * 
	 * @param point
	 *            token point
	 * @return true if point == outer wall, false otherwise
	 */
	public boolean isOuterWall(Point point) {
		if (point.x <= 0 || point.y <= 0)
			return true;

		else if (point.x >= LAST_COLUM_INDEX || point.y >= LAST_ROW_INDEX)
			return true;

		return false;
	}
}
