package Engine;

import Engine.Components.Physical.Position;
import abstraction.IEntity;

import java.util.ArrayList;
import java.util.UUID;

public class Tile {
	
	
	public Tile(TerrainTypes terrainType){
		this.terrainType = terrainType;
		this.isPassable = true;
	}
	
	private TerrainTypes terrainType;
	private Boolean isPassable;

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

	public void setPassable(Boolean passable) {
		isPassable = passable;
	}

	public Boolean getPassable() {
		return isPassable;
	}
}
