package Engine;

import abstraction.IEntity;

import java.util.ArrayList;
import java.util.UUID;

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

	private ArrayList<IEntity> entitiesOnTile = new ArrayList<>();


	public ArrayList<IEntity> getEntitiesOnTile() {
		return entitiesOnTile;
	}
}
