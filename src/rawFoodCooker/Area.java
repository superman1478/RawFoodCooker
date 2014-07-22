package rawFoodCooker;

import org.powerbot.script.Tile;

public enum Area {

	/*Areas are declared an extra tile north and east because the contains method excludes the 
	northmost and eastmost tiles.*/
	EDGEVILLE (new org.powerbot.script.Area(new Tile(3075, 3486, 0), new Tile(3099, 3499, 0))),
	CATHERBY (new org.powerbot.script.Area(new Tile(2804, 3433, 0), new Tile(2822, 3448, 0))),
	ROGUES_DEN (new org.powerbot.script.Area(new Tile(3025, 4947, 0), new Tile(3036, 4962, 0)));

	private final org.powerbot.script.Area area;

	Area(org.powerbot.script.Area myArea) {
		area = myArea;
	}

	public org.powerbot.script.Area getArea() { 
		return area; 
	}

}
