package Engine.Components.ChunksAndTiles;

import java.util.*;

import com.jogamp.nativewindow.util.Point;

import Engine.Infrastructure.Constants;
import Engine.Infrastructure.TerrainTypes;
import abstraction.IChunkProvider;

public class ChunkData implements IChunkProvider {
	private HashMap<Point,Chunk> chunks;

	private HashMap<Point,Chunk> realityBubbleChunks;

	TerrainGenerator terrainGenerator;
	public ChunkData()
	{
		Id = UUID.randomUUID();
		chunks = new HashMap<Point,Chunk>();

		terrainGenerator = new TerrainGenerator(5);
		realityBubbleChunks =  new HashMap<Point,Chunk> ();
		initWorld();
	}
	
	void initWorld()
	{
		int testWorldSize = 1000;
		int offfset = 0;
		for(int x = offfset; x<=testWorldSize+offfset; x++){
			for(int y = offfset; y<=testWorldSize+offfset; y++){
				CreateChunk(x,y);
			}			
		}
	}

	public void CreateChunk(int x,int y)
	{
		Chunk newChunk = new Chunk(new Point(x*Constants.ChunkSize,y*Constants.ChunkSize),this);
		//newChunk.FillWithTiles(terrainGenerator);
		chunks.put(new Point(x,y), newChunk);
	}
	public void RemoveChunk(int x,int y)
	{
		chunks.remove(new Point(x,y));
	}


	public Tile getTileAt(Point p)
	{
		return getTile(p.getX(),p.getY());	
	}
	//TODO: we need to implement quick iteration by using bounding box trees;
	public Tile getTile(int x,int y)
	{
		Chunk chunkOfPoint = null;
		for(Iterator<Chunk> i = realityBubbleChunks.values().iterator(); i.hasNext(); ) {
			Chunk ch = i.next();
			if(ch.isPointInside(x,y))
			{
				chunkOfPoint = ch;
				break;
			}
		}
		if(chunkOfPoint==null)
		{
			return new Tile(TerrainTypes.Nothingness, new Point(-1, -1));
		}
		return chunkOfPoint.getTile(x, y);	
	}
	//TODO: we need to implement quick iteration by using bounding box trees;
	public boolean setTile(int x,int y, Tile tile)
	{
		Chunk chunkOfPoint = null;
		for(Iterator<Chunk> i = realityBubbleChunks.values().iterator(); i.hasNext(); ) {
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


	public TerrainGenerator getWorldGenerator() {
		return terrainGenerator;
	}

	public HashMap<Point,Chunk> getRealityBubbleChunks() {
		return realityBubbleChunks;
	}

	public HashMap<Point,Chunk> getChunks() {
		return chunks;
	}

	UUID Id;

	@Override
	public UUID getId() {
		return Id;
	}
}
