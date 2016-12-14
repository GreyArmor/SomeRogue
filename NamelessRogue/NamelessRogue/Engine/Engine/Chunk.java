package Engine;

import com.jogamp.nativewindow.util.Point;

public class Chunk {
	private Point worldPositionBottomLeftCorner;

	private Tile[][] chunkTiles;
	private BoundingBox boundingBox;
	
		
	public Point getWorldPosition() {
		return worldPositionBottomLeftCorner;
	}

	public void setWorldPosition(Point worldPosition) {
		this.worldPositionBottomLeftCorner = worldPosition;
	}	
	
	public Chunk(Point bottomLeftCornerWorld)
	{
		boundingBox = new BoundingBox(bottomLeftCornerWorld, new Point(bottomLeftCornerWorld.getX()+Constants.ChunkSize,bottomLeftCornerWorld.getY()+Constants.ChunkSize));
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
				else if(x>Constants.ChunkSize/2)
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Water);
				}
				else
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Dirt);
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
		return boundingBox.isPointInside(x,y);	
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

	public void setTile(int x, int y, Tile tile) {
		// TODO Auto-generated method stub
		
	}

	BoundingBox getBoundingBox() {
		return boundingBox;
	}

	void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
}
