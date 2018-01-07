package abstraction;

import Engine.Chunk;
import Engine.Components.Component;
import Engine.Components.IComponent;
import Engine.Tile;
import com.jogamp.nativewindow.util.Point;

import java.util.HashMap;


public interface IWorldProvider extends IComponent {
	public Tile getTile(int x,int y);
	public boolean setTile(int x,int y, Tile tile);
	public HashMap<Point,Chunk> getRealityBubbleChunks();
	public HashMap<Point,Chunk> getChunks();
}
