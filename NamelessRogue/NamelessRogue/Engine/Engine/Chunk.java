package Engine;

import com.jogamp.nativewindow.util.Point;

public class Chunk {
	private Point worldPositionBottomLeftCorner;

	private Tile[][] chunkTiles;
		
	public Point getWorldPosition() {
		return worldPositionBottomLeftCorner;
	}

	public void setWorldPosition(Point worldPosition) {
		this.worldPositionBottomLeftCorner = worldPosition;
	}	
	
	public Chunk(Point bottomLeftCornerWorld)
	{
		worldPositionBottomLeftCorner = bottomLeftCornerWorld;
		chunkTiles = new Tile[Constants.ChunkSize][Constants.ChunkSize];		
	}
	
	public void fillWithTestTiles()
	{
		for(int x = 0;x<Constants.ChunkSize;x++)
		{
			for(int y = 0;y<Constants.ChunkSize;y++)
			{
				if(x==0||y==0||x == Constants.ChunkSize-1||y == Constants.ChunkSize-1 )
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Road);
				}
				else
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Water);
				}
			}
		}		
		
	}

	public Tile[][] getChunkTiles() {
		return chunkTiles;
	}

	public void setChunkTiles(Tile[][] chunkTiles) {
		this.chunkTiles = chunkTiles;
	}

	public boolean isPointInside(Point p) {
		return isPointInside(p.getX(),p.getY());	
	}
	public boolean isPointInside(int x, int y) {
		int bottomLeftX = worldPositionBottomLeftCorner.getX();
		int bottomLeftY = worldPositionBottomLeftCorner.getY();
		int topRightX = worldPositionBottomLeftCorner.getX() + Constants.ChunkSize;
		int topRightY = worldPositionBottomLeftCorner.getY() + Constants.ChunkSize;
		
		if(x>=bottomLeftX && x<topRightX&&y>=bottomLeftY&&y<topRightY){
			return true;
		}
		else{
			return false;
		}
	}

	public Tile getTile(Point p) {
		return getTile(p.getX(),p.getY());
	}
	
	public Tile getTile(int x,int y) {
		int bottomLeftX = worldPositionBottomLeftCorner.getX();
		int bottomLeftY = worldPositionBottomLeftCorner.getY();
		
		int localX = Math.abs(bottomLeftX - x);
		int localY = Math.abs(bottomLeftY - y);
		
		return chunkTiles[localX][localY];
	}
}
