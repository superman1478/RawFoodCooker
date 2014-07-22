package rawFoodCooker;

public enum RawFood {
	
	SHRIMP (317),
	SARDINE (327),
	ANCHOVIES (321),
	MACKEREL (353),
	TROUT (335),
	SALMON (331),
	TUNA (359),
	LOBSTER (377),
	SWORDFISH (371),
	MONKFISH (7944),
	SHARK (383),
	CAVEFISH (15264),
	ROCKTAIL (15270),
	SWEET_CORN (5986),
	POTATO (1942),
	RAT_MEAT (2134),
	BEEF (2132);

	private final int id;

	RawFood(int id) {
		this.id = id;
	}

	public int getId() { 
		return id; 
	}

	@Override
	public String toString() {
		return name().toLowerCase().replace("_", " ");
	}

}
