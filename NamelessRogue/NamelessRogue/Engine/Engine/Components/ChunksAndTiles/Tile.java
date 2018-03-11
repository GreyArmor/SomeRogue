package Engine.Components.ChunksAndTiles;

import Engine.Infrastructure.Entity;
import Engine.Infrastructure.TerrainTypes;
import abstraction.IEntity;
import com.jogamp.nativewindow.util.Point;

import java.util.ArrayList;

public class Tile {

	public Tile(){}
	
	public Tile(TerrainTypes terrainType, Point coordinate){
		this.terrainType = terrainType;
		this.coordinate = coordinate;
		this.isPassable = true;
	}
	private TerrainTypes terrainType;
	private Point coordinate;
	private Boolean isPassable;
	private transient ArrayList<Entity> entitiesOnTile = new ArrayList<>();

	public TerrainTypes getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(TerrainTypes terrainType) {
		this.terrainType = terrainType;
	}



	public ArrayList<Entity> getEntitiesOnTile() {
		return entitiesOnTile;
	}

	public void setPassable(Boolean passable) {
		isPassable = passable;
	}

	public Boolean getPassable() {
		return isPassable;
	}


	public Point getCoordinate() {
		return coordinate;
	}
}
