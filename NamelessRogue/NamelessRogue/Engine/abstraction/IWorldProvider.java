package abstraction;

import Engine.Chunk;
import Engine.Tile;
import com.jogamp.nativewindow.util.Point;

import java.util.HashMap;


public interface IWorldProvider {
	public Tile getTile(int x,int y);
	public boolean setTile(int x,int y, Tile tile);
	public HashMap<Point,Chunk> getRealityBubbleChunks();
	public HashMap<Point,Chunk> getChunks();
}
