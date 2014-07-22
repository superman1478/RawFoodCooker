package rawFoodCooker;

import org.powerbot.script.Tile;
import org.powerbot.script.Area;

public enum AreaInfo {
	

	CATHERBY (new Area(new Tile(2804, 3433, 0), new Tile(2822, 3448, 0)),
			new Tile(2817, 3443, 0), //cooking tile
			new Tile(2809, 3441, 0), //banking tile
			new Tile(2816, 3438, 0), //closedDoorTile
			2728, //rangeId
			"Range"),
	EDGEVILLE (new Area(new Tile(3075, 3486, 0), new Tile(3099, 3499, 0)),
			new Tile(3078, 3495, 0),
			new Tile(3094, 3491, 0),
			new Tile (3079, 3497, 0),
			12269,
			"Stove"),
	ROGUES_DEN (new Area(new Tile(3025, 4947, 0), new Tile(3036, 4962, 0)),
			new Tile(3029, 4958, 0),
			new Tile(3029, 4958, 0),
			Tile.NIL, // no closed door
			2732,
			"Fire"),
	;

	private final Area area;
	private final Tile cookingTile;
	private final Tile bankingTile;
	private final int rangeId;
	private final Tile closedDoorTile;
	private final String hotSurfaceName;
	
	private AreaInfo(Area area, Tile cookingTile, Tile bankingTile,
			Tile closedDoorTile, int rangeId,
			String hotSurfaceName) {
		this.area = area;
		this.cookingTile = cookingTile;
		this.bankingTile = bankingTile;
		this.rangeId = rangeId;
		this.closedDoorTile = closedDoorTile;
		this.hotSurfaceName = hotSurfaceName;
	}
	
	public Area getArea() {
		return area;
	}

	public Tile getCookingTile() {
		return cookingTile;
	}
	
	public Tile getBankingTile() {
		return bankingTile;
	}

	public int getRangeId() {
		return rangeId;
	}

	public Tile getClosedDoorTile() {
		return closedDoorTile;
	}

	public String getHotSurfaceName() {
		return hotSurfaceName;
	}

}
