package Engine.Components;

import java.util.HashMap;
import java.util.Iterator;

import com.jogamp.nativewindow.util.Point;

import Engine.Chunk;
import Engine.Constants;
import Engine.TerrainTypes;
import Engine.Tile;
import abstraction.IWorldProvider;

public class ChunkData implements IWorldProvider {
	HashMap<Point,Chunk> chunks;
	public ChunkData()
	{
		chunks = new HashMap<Point,Chunk>();
		initTestWorld();
	}
	
	void initTestWorld()
	{
		int testWorldSize = 5;
		for(int x = -testWorldSize; x<=testWorldSize; x++){
			for(int y = -testWorldSize; y<=testWorldSize; y++){
				Chunk newChunk = new Chunk(new Point(x*Constants.ChunkSize,y*Constants.ChunkSize));
				newChunk.fillWithTestTiles();
				chunks.put(new Point(x,y), newChunk);
			}			
		}
	}
	
	
	public Tile getTileAt(Point p)
	{
		return getTile(p.getX(),p.getY());	
	}
	
	public Tile getTile(int x,int y)
	{
		Chunk chunkOfPoint = null;
		for(Iterator<Chunk> i = chunks.values().iterator(); i.hasNext(); ) {
			Chunk ch = i.next();
			if(ch.isPointInside(x,y))
			{
				chunkOfPoint = ch;
				break;
			}
		}
		if(chunkOfPoint==null)
		{
			return new Tile(TerrainTypes.Nothingness);
		}
		return chunkOfPoint.getTile(x, y);	
	}
	
	public boolean setTile(int x,int y, Tile tile)
	{
		Chunk chunkOfPoint = null;
		for(Iterator<Chunk> i = chunks.values().iterator(); i.hasNext(); ) {
			Chunk ch = i.next();
			if(ch.isPointInside(x,y))
			{
				chunkOfPoint = ch;
				break;
			}
		}
		if(chunkOfPoint==null)
		{
			return false;
		}
		chunkOfPoint.setTile(x, y,tile);	
		return true;
	}
	
	

}
