package se.fidde.murder.model.gridTiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Inner walls class for storing wall tiles
 * 
 * @author fidde
 * 
 */
public enum InnerWalls {
	INSTANCE;

	private List<List<Point>> walls = null;

	private InnerWalls() {
		if (walls == null) {
			this.walls = new ArrayList<>();
			this.walls.add(kitchenWalls());
			this.walls.add(ballroomWalls());
			this.walls.add(conservatoryWalls());
			this.walls.add(billjardRoomWalls());
			this.walls.add(libraryWalls());
			this.walls.add(studyWalls());
			this.walls.add(hallWalls());
			this.walls.add(loungeWalls());
			this.walls.add(diningRoomWalls());

			for (List<Point> list : walls) {
				list = removeDoorsFrom(list);
			}

		}
	}

	private List<Point> removeDoorsFrom(List<Point> list) {
		List<Point> tilesToRemove = new ArrayList<>();
		for (Point point : list) {
			for (Doors door : Doors.values()) {
				if (door.isOnTile(point))
					tilesToRemove.add(point);
			}
		}

		for (Point index : tilesToRemove)
			list.remove(index);

		return list;
	}

	/**
	 * Checks if point is on a wall tile
	 * 
	 * @param point
	 *            token position
	 * @return true if point == wall, false otherwise
	 */
	public boolean isInnerWall(Point point) {
		for (List<Point> list : walls) {
			for (Point wallTile : list) {
				if (wallTile.x == point.x && wallTile.y == point.y)
					return true;
			}
		}
		return false;
	}

	private List<Point> kitchenWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(5, 1);
		list = addVerticalTiles(list, wallStart, 5);
		list = addHorizontalTiles(list, wallStart, 2);

		wallStart.move(1, 6);
		list = addHorizontalTiles(list, wallStart, 5);

		wallStart.move(4, 5);
		list.add(wallStart);
		return list;
	}

	private List<Point> ballroomWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(10, 1);
		list.add(new Point(wallStart.x, wallStart.y));

		wallStart.move(8, 2);
		list = addVerticalTiles(list, wallStart, 5);
		list = addHorizontalTiles(list, wallStart, 2);

		wallStart.move(8, 7);
		list = addHorizontalTiles(list, wallStart, 8);

		wallStart.move(13, 1);
		list.add(new Point(wallStart.x, wallStart.y));

		wallStart.move(14, 2);
		list = addHorizontalTiles(list, wallStart, 2);

		wallStart.move(15, 2);
		list = addVerticalTiles(list, wallStart, 5);

		wallStart.move(9, 6);
		list.add(new Point(wallStart.x, wallStart.y));

		wallStart.move(14, 5);
		list.add(wallStart);

		return list;
	}

	private List<Point> conservatoryWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(17, 1);
		list.add(new Point(wallStart.x, wallStart.y));

		wallStart.move(18, 1);
		list = addVerticalTiles(list, wallStart, 4);

		wallStart.move(20, 5);
		list = addHorizontalTiles(list, wallStart, 3);

		wallStart.move(19, 4);
		list.add(wallStart);

		return list;
	}

	private List<Point> billjardRoomWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(18, 8);
		list = addHorizontalTiles(list, wallStart, 5);
		list = addVerticalTiles(list, wallStart, 5);

		wallStart.move(18, 12);
		list = addHorizontalTiles(list, wallStart, 5);

		wallStart.move(19, 9);
		list.add(wallStart);

		return list;
	}

	private List<Point> libraryWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(17, 15);
		list = addVerticalTiles(list, wallStart, 3);

		wallStart.move(18, 14);
		list = addHorizontalTiles(list, wallStart, 5);

		wallStart.move(18, 18);
		list = addHorizontalTiles(list, wallStart, 5);

		wallStart.move(18, 16);
		list.add(wallStart);

		return list;
	}

	private List<Point> studyWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(18, 21);
		list = addHorizontalTiles(list, wallStart, 5);

		wallStart.move(17, 22);
		list = addVerticalTiles(list, wallStart, 2);

		return list;
	}

	private List<Point> hallWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(9, 18);
		list = addHorizontalTiles(list, wallStart, 6);
		list = addVerticalTiles(list, wallStart, 6);

		wallStart.move(14, 18);
		list = addVerticalTiles(list, wallStart, 6);

		wallStart.move(12, 19);
		list.add(wallStart);

		return list;
	}

	private List<Point> loungeWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(1, 19);
		list = addHorizontalTiles(list, wallStart, 5);

		wallStart.move(6, 20);
		list = addVerticalTiles(list, wallStart, 4);

		return list;
	}

	private List<Point> diningRoomWalls() {
		List<Point> list = new ArrayList<>();
		Point wallStart = new Point(1, 9);
		list = addHorizontalTiles(list, wallStart, 4);

		wallStart.move(5, 10);
		list = addHorizontalTiles(list, wallStart, 3);

		wallStart.move(7, 10);
		list = addVerticalTiles(list, wallStart, 6);

		wallStart.move(1, 15);
		list = addHorizontalTiles(list, wallStart, 7);

		wallStart.move(6, 14);
		list.add(wallStart);

		return list;
	}

	private List<Point> addVerticalTiles(List<Point> list, Point start,
			int offset) {
		assert (offset + start.y) >= 0;

		int col = start.x;
		int row = start.y;
		offset = offset + row;

		for (int i = row; i < offset; i++)
			list.add(new Point(col, i));

		return list;
	}

	private List<Point> addHorizontalTiles(List<Point> list, Point start,
			int offset) {
		assert (start.x + offset) >= 0;
		int col = start.x;
		int row = start.y;
		offset = offset + col;

		for (int i = col; i < offset; i++)
			list.add(new Point(i, row));

		return list;
	}
}
