package abstraction;

import Engine.Tile;

public interface IWorldProvider {
	public Tile getTile(int x,int y);
	public boolean setTile(int x,int y, Tile tile);
}
