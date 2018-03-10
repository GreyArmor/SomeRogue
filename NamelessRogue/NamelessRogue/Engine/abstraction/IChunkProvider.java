package abstraction;

import Engine.Components.ChunksAndTiles.Chunk;
import Engine.Components.IComponent;
import Engine.Components.ChunksAndTiles.Tile;
import com.jogamp.nativewindow.util.Point;

import java.util.HashMap;


public interface IChunkProvider extends IComponent {
	public Tile getTile(int x,int y);
	public boolean setTile(int x,int y, Tile tile);
	public HashMap<Point,Chunk> getRealityBubbleChunks();
	public HashMap<Point,Chunk> getChunks();
}
