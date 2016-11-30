package Engine;

import java.util.HashMap;

import com.jogamp.nativewindow.util.Point;

public class World {
	HashMap<Point,Chunk> chunks;
	public World()
	{
		chunks = new HashMap<Point,Chunk>();
	}
	
	
	public Tile getTileAt(Point p)
	{
		return new Tile();		
	}

}
