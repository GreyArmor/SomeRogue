package Engine;

public class Tile {
	
	
	public Tile(TerrainTypes terrainType){
		this.terrainType = terrainType;
	}
	
	private TerrainTypes terrainType;

	public TerrainTypes getTerrainType() {
		return terrainType;
	}


	public void setTerrainType(TerrainTypes terrainType) {
		this.terrainType = terrainType;
	}

}
