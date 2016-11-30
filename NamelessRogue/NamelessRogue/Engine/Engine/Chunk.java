package Engine;

import com.jogamp.nativewindow.util.Point;

public class Chunk {
	private Point worldPosition;

	private Tile[][] chunkTiles;
		
	public Point getWorldPosition() {
		return worldPosition;
	}

	public void setWorldPosition(Point worldPosition) {
		this.worldPosition = worldPosition;
	}	
	
	public Chunk(Point bottomLeftCornerWorld)
	{
		worldPosition = bottomLeftCornerWorld;
		chunkTiles = new Tile[Constants.ChunkSize][Constants.ChunkSize];		
	}

	public Tile[][] getChunkTiles() {
		return chunkTiles;
	}

	public void setChunkTiles(Tile[][] chunkTiles) {
		this.chunkTiles = chunkTiles;
	}
}
